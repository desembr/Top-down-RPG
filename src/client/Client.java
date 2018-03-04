package client;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import server.entities.Player;

/**
 * Interface between players and the server, passes updated information returned
 * from the server back to the calling UserInterface through setChanged() and
 * notifyObservers() methods of the Observable base class.
 * 
 * @author Tom Bjurenlind
 * @version 2018-02-24
 */
public class Client extends Observable {
	private Socket clientSocket;

	private Thread sendThread, recvThread;

	private static final int serverPort = 44120;
	private static final String serverAddress = "localhost";

	private ObjectInputStream recvStream;
	private ObjectOutputStream sendStream;

	private List<String> polledCommands; // UserInterface places user input commands in here, for sending.

	private int counter = 0; // Attempts to detect that the server has gone down. 
	

	/**
	 * Constructor for Client.
	 */
	public Client() {
		sendThread = new Thread(this::send);
		recvThread = new Thread(this::receive);
		polledCommands = new ArrayList<>();
		
		try {
			clientSocket = new Socket(serverAddress, serverPort);
			OutputStream os = clientSocket.getOutputStream();
			os.flush();
			sendStream = new ObjectOutputStream(os);
			sendStream.flush();
			InputStream is = clientSocket.getInputStream();
			recvStream = new ObjectInputStream(is);
		} catch (SocketException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (UnknownHostException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		
	}

	/**
	 * Starts this client's threads to start communication with the server.
	 */
	public void start() {
		sendThread.start();
		recvThread.start();
	}

	/**
	 * The sendThread method sending commands to Server.
	 */
	private void send() {
		while (!Thread.interrupted()) {
			synchronized (polledCommands) {
				// Server most likely went down, exit.
				if (getErrorCount() > 3) {
					break;
				}
				// Wait for user input (some command request).
				while (polledCommands.isEmpty()) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						System.err.println(e.getMessage());
					}
				}
				// Send request to execute a command to server.
				String nextCmd = polledCommands.remove(0);
				sendCommand(nextCmd);
			}
		}
		synchronized (this) {
			exit();
		}
		System.exit(1);
	}

	/**
	 * The receiveThread method listening for response from Server.
	 */
	private void receive() {
		while (!Thread.interrupted()) {
			// Server most likely went down, exit.
			if (getErrorCount() > 3) {
				break;
			}
			// Wait for reply from server on a previous request.
			receiveResponse();
		}
		synchronized (this) {
			exit();
		}
		System.exit(1);
	}

	/**
	 * Called by UserInterface on user-entered commands to queue for
	 * transmission to server.
	 * 
	 * @param cmdLine
	 */
	public synchronized void pollCommand(String cmdLine) {
		polledCommands.add(cmdLine);
	}

	/**
	 * Sends user-input command to server for processing.
	 * 
	 * @param cmdLine
	 *            The full command, including option/parameter, input by user.
	 */
	private void sendCommand(String cmdLine) {
		try {
			sendStream.writeObject(cmdLine);
			sendStream.flush();
			resetErrorCounter();
		} catch (IOException e) {
			incErrorCounter();
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Blocks until response is received from server, then calls the gui's
	 * update method if state changed for this user's player object as a
	 * consequence of input command.
	 */
	private void receiveResponse() {
		try {
			Object o = (Object) recvStream.readObject();
			Player p = (Player) o;
			// Update UserInterface with the updated game state.
			setChanged();
			notifyObservers(p);
			
			resetErrorCounter();
		} catch (EOFException e) {
			incErrorCounter();
			System.err.println(e.getMessage());
		} catch (IOException e) {
			incErrorCounter();
			System.err.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			incErrorCounter();
			System.err.println(e.getMessage());
		}
	}
	

	/**
	 * Increments error-counter.
	 */
	private synchronized void incErrorCounter() {
		counter++;
	}

	/**
	 * Resets error-counter.
	 */
	private synchronized void resetErrorCounter() {
		counter = 0;
	}
	
	/**
	 * Gets the number of errors (exceptions) in sequence currently. 3
	 * exceptions in sequence indicates that the server has
	 * gone down, meaning this Client should dispose itself.
	 * 
	 * @return The current amount of errors in sequence.
	 */
	private synchronized int getErrorCount() {
		return counter;
	}

	/**
	 * Disposes of this Client object, closes all streams and sockets.
	 */
	public synchronized void exit() {
		try {
			if (sendStream != null)
				sendStream.close();
			if (recvStream != null)
				recvStream.close();
			if (clientSocket != null && !clientSocket.isClosed())
				clientSocket.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	

}
