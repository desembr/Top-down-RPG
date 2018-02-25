package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import server.entities.Player;

/**
 * Handles client-server communication, one ClientHandler object for every
 * connected Client.
 * 
 * @author Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 2018-02-28
 */
public class ClientHandler {
	private Socket clientSocket;
	private GameEngine engine;

	private Thread sendThread, recvThread;

	private ObjectInputStream recvStream;
	private ObjectOutputStream sendStream;

	private Player p;

	private int counter = 0; // Attempts to detect client disconnect.

	// For checking when to update other clients in same room,
	// and when to update the handled client.
	private boolean stateUpdated = true, playerDisconnect, concurrentStateChange, updateOthers;

	/**
	 * Constructor for ClientHandler.
	 * 
	 * @param clientSocket
	 *            The client's socket to communicate with.
	 * @param engine
	 *            The GameEngine of the server, which processes all commands.
	 * @param p
	 *            The player object corresponding to the handled Client.
	 */
	public ClientHandler(Socket clientSocket, GameEngine engine, Player p) {
		this.clientSocket = clientSocket;
		this.engine = engine;
		this.p = p;

		updateOthers = true; // Update other clients when new client connects.

		sendThread = new Thread(this::send);
		recvThread = new Thread(this::receive);

		try {
			OutputStream os = this.clientSocket.getOutputStream();
			os.flush();
			sendStream = new ObjectOutputStream(os);
			sendStream.flush();
			InputStream is = this.clientSocket.getInputStream();
			recvStream = new ObjectInputStream(is);
			sendThread.start();
			recvThread.start();

			setStateUpdated(true); // Signal to update all clients in same room.
		} catch (SocketException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			return;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			return;
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			return;
		}
	}

	/**
	 * The sendThread method sending responses to the handled Client.
	 */
	private void send() {
		while (!Thread.interrupted()) {
			if (getErrorCount() > 3 || (getPlayerDisconnect() && !getStateUpdated() && !getConcurrentStateChange())) {
				break;
			}
			// Wait for some game state to change by handled client or
			// some other client currently connected.
			while (!getStateUpdated() && !getConcurrentStateChange()) {
				if (getErrorCount() > 3 || getPlayerDisconnect())
					break;
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				}
			}
			// Regular state change initiated by the handled Client. Update
			// other clients in same room as this handled client's.
			if (getStateUpdated()) {
				if (shouldUpdateOthers()) {
					List<ClientHandler> clientHandlers = GameServer.getClientHandlers(p);

					for (ClientHandler ch : clientHandlers)
						if (ch != this)
							ch.setConcurrentStateChange(true);
				}
			} else { // Concurrent state change initiated by some other Client
						// in the same room as this handled client's. Don't
						// update others.
				setConcurrentStateChange(false);
				if (p.getIsDead()) {
					List<ClientHandler> clientHandlers = GameServer.getClientHandlers(p);

					for (ClientHandler ch : clientHandlers)
						if (ch != this)
							ch.setConcurrentStateChange(true);
				}
			}

			setStateUpdated(false);
			sendResponse();
			resetErrorCounter();
		}
		engine.removePlayer(p);
		GameServer.removeClientHandler(this);
	}

	/**
	 * The receiveThread method listening for commands from the handled Client.
	 */
	private void receive() {
		while (!Thread.interrupted()) {
			if (getErrorCount() > 3 || getPlayerDisconnect()) {
				break;
			}
			// Wait for request from the handled client.
			try {
				String cmdLine = (String) recvStream.readObject();

				// Only update other clients in same room if room state changed
				// in some way.
				boolean roomStateChange = engine.interpretCommand(cmdLine, p);
				if (roomStateChange) {
					setShouldUpdateOthers(true);
				} else {
					setShouldUpdateOthers(false);
				}

				setStateUpdated(true);

				resetErrorCounter();

				if (p.getIsDead()) {
					setPlayerDisconnect(true);
					break;
				}

			} catch (EOFException e) {
				System.err.println(e.getMessage());
				incErrorCounter();
				break;
			} catch (ClassNotFoundException e) {
				System.err.println(e.getMessage());
			} catch (IOException e) {
				incErrorCounter();
				System.err.println(e.getMessage());
			}
		}
		setPlayerDisconnect(true);
	}

	/**
	 * Sends response back to client.
	 */
	private void sendResponse() {
		synchronized (engine) { // To avoid concurrentModificationException
			try {
				sendStream.writeObject(p);
				sendStream.flush();
				sendStream.reset();

				p.setCmdReturnMsg((null));
			} catch (IOException e) {
				incErrorCounter();
				System.err.println(e.getMessage());
			}
		}
	}

	/**
	 * Gets the player object of this ClientHandler.
	 * 
	 * @return The player object.
	 */
	public synchronized Player getPlayer() {
		return p;
	}

	/**
	 * Gets set to true when a command has been executed.
	 * 
	 * @param updated
	 *            To indicate whether an update has taken place or not.
	 */
	private synchronized void setStateUpdated(boolean updated) {
		stateUpdated = updated;
	}

	/**
	 * Indicates whether a response should be sent back to a client.
	 * 
	 * @return Whether a response should be sent back to client or not.
	 */
	private synchronized boolean getStateUpdated() {
		return stateUpdated;
	}

	/**
	 * Sets disconnected to true if client has initiated a disconnect (exit
	 * command).
	 * 
	 * @param disconnected
	 *            Whether to flag that Exit command has been received and
	 *            processed.
	 */
	private synchronized void setPlayerDisconnect(boolean disconnected) {
		playerDisconnect = disconnected;
	}

	/**
	 * Indicates whether an Exit command has been received and processed and
	 * that this ClientHandler therefore should dispose of itself.
	 * 
	 * @return Whether an Exit command has been received and processed.
	 */
	private synchronized boolean getPlayerDisconnect() {
		return playerDisconnect;
	}

	/**
	 * Sets concurrentStateChange to true if some other client whose player
	 * object resides in the same room as this one's has modified some state of
	 * that room.
	 * 
	 * @param stateChanged
	 *            True to signal a concurrent state change.
	 */
	private synchronized void setConcurrentStateChange(boolean stateChanged) {
		concurrentStateChange = stateChanged;
	}

	/**
	 * Returns the concurrentStateChange status, true if a concurrent state
	 * change has occurred.
	 * 
	 * @return True if a concurrent state change has occurred in this
	 *         ClientHandler's player object's room by some other Client also
	 *         residing in that room.
	 */
	private synchronized boolean getConcurrentStateChange() {
		return concurrentStateChange;
	}

	/**
	 * Increments the current sequenced error count on exception on any
	 * operation.
	 */
	private synchronized void incErrorCounter() {
		counter++;
	}

	/**
	 * Resets the error count on successful operation.
	 */
	private synchronized void resetErrorCounter() {
		counter = 0;
	}

	/**
	 * Gets the number of errors (exceptions) in sequence currently. 3
	 * exceptions in sequence indicates that the handled Client has
	 * disconnected, meaning this ClientHandler should dispose itself.
	 * 
	 * @return The current amount of errors in sequence.
	 */
	private synchronized int getErrorCount() {
		return counter;
	}

	/**
	 * Set whether this ClientHandler should update the others.
	 * 
	 * @param should
	 *            Whether this ClientHandler should update the others.
	 */
	private synchronized void setShouldUpdateOthers(boolean should) {
		updateOthers = should;
	}

	/**
	 * Get whether this ClientHandler should update the others.
	 * 
	 * @return Whether this ClientHandler should update the others.
	 */
	private synchronized boolean shouldUpdateOthers() {
		return updateOthers;
	}
}
