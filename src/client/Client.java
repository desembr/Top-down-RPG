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

import server.Player;

/**
 * Interface between players and the server, passes updated information returned from
 * the server back to the calling gui through setChanged() and notifyObservers() methods
 * of the Observable base class.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Client extends Observable implements Runnable {
	private Socket clientSocket;
	
	private Thread thread;
	
	private static final int serverPort = 44120;
	private static final String serverAddress = "localhost";
	
	private ObjectInputStream recvStream;
	private ObjectOutputStream sendStream;
	
	private List<String> polledCommands;	
	
	private static int count = 0;
	
	/**
	 * Constructor for Client.
	 */
	public Client() {
		thread = new Thread(this);
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
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		//System.out.println("You are connected to server " + serverAddress + ":" + serverPort);
	}
	
	/**
	 * Starts this client's thread to start communicate with server.
	 */
	public void start() {
		thread.start();
	}
	
	/**
	 * The continuous loop handling sending of input commands as well as listening
	 * for response from server program. 
	 */
	public void run() {
		// Receive initial game state from server (the player object on the server,
		// associated with this Client).
		receiveResponse();
		
		while (!Thread.interrupted()) {
				synchronized (polledCommands) {
					if (count > 3) {
						break;
					}
					if (polledCommands.isEmpty()) {
						while (polledCommands.isEmpty()) {
						try {
							Thread.sleep(500);
							} catch (InterruptedException e) {
								System.err.println(e.getMessage());
							}
						} 
					}
					String nextCmd = polledCommands.remove(0);
					sendCommand(nextCmd);
				}
				//notify();
				receiveResponse();
		}
		try {
			if (recvStream != null)
				recvStream.close();
			if (sendStream != null)
				sendStream.close();
			if (clientSocket != null)
				clientSocket.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Called by UserInterface on user-entered commands to queue for transmission to server.
	 * @param cmdLine
	 */
	public synchronized void pollCommand(String cmdLine) {
		polledCommands.add(cmdLine);
		//notify();
	}
	
	/**
	 * Sends user-input command to server for processing.
	 * @param cmdLine The full command, including option/parameter, input by user.
	 */
	private void sendCommand(String cmdLine) {
		try {
			//System.out.println("Transmitting command to Server...");
			sendStream.writeObject(cmdLine);
			sendStream.flush();
			System.out.println("Transmitted command to Server");
		} catch (IOException e) {
			count++;
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Blocks until response is received from server, then calls the gui's update method
	 * if state changed for this user's player object as a consequence of input command.
	 */
	private void receiveResponse() {
		try {
			//String msg = (String)recvStream.readObject();
			System.out.println("Waiting for response from Server...");
			Object o = (Object)recvStream.readObject();
			Player p = (Player)o;
			System.out.println("Received response from Server: " + p.getName());
			setChanged();
			notifyObservers(p);
		} catch (EOFException e) {
			count++;
			System.err.println(e.getMessage()); 
		}catch (IOException e) {
			count++;
			System.err.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			count++;
			System.err.println(e.getMessage());
		}
	}
}
