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

/**
 * Handles client-server communication, one ClientHandler object for every
 * connected Client.
 * 
 * @author Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class ClientHandler {
	private Socket clientSocket;
	private GameEngine engine;

	private Thread sendThread, recvThread;

	private ObjectInputStream recvStream;
	private ObjectOutputStream sendStream;

	private Player p;

	private int counter = 0; // Attempts to detect client disconnect.

	private boolean stateUpdated = true, playerDisconnect;

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

		// Sends initial game state for the newly connected client to display in
		// Client program GUI.
		// sendResponse();
	}

	public synchronized void setStateUpdated(boolean updated) {
		stateUpdated = updated;
	}

	public synchronized boolean getStateUpdated() {
		return stateUpdated;
	}

	public synchronized void setPlayerDisconnect(boolean disconnected) {
		playerDisconnect = disconnected;
	}

	public synchronized boolean getPlayerDisconnect() {
		return playerDisconnect;
	}

	public void send() {
		while (!Thread.interrupted()) {
			if (getErrorCount() > 3 || getPlayerDisconnect()) {
				break;
			}
			while (!getStateUpdated()) {
				if (getPlayerDisconnect())
					break;
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				}
			}
			setStateUpdated(false);
			sendResponse();
			resetErrorCounter();
		}
		engine.removePlayer(p);
	}

	public void receive() {
		while (!Thread.interrupted()) {
			if (getErrorCount() > 3) {
				break;
			}

			try {
				System.out.println("Waiting for command from a client...");
				String cmdLine = (String) recvStream.readObject();
				System.out.println("Received command from a Client");

				engine.interpretCommand(cmdLine, p);

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
	 * 
	 * @param respMsg
	 *            A message to return to the client, in addition to the Player
	 *            object.
	 */
	public void sendResponse() {
		try {
			sendStream.writeObject(p);
			sendStream.flush();
			sendStream.reset();
			System.out.println("Transmitted response back to a Client");
		} catch (IOException e) {
			incErrorCounter();
			System.err.println(e.getMessage());
		}
	}

	private synchronized void incErrorCounter() {
		counter++;
	}

	private synchronized void resetErrorCounter() {
		counter = 0;
	}

	private synchronized int getErrorCount() {
		return counter;
	}
}
