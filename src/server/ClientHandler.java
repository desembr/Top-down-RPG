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
 * Handles client-server communication, one ClientHandler object for every connected Client.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class ClientHandler implements Runnable {
	private Socket clientSocket;
	private GameEngine engine;
	
	private Thread thread;
	
	private ObjectInputStream recvStream;
	private ObjectOutputStream sendStream;
	
	private Player p;
	
	private static int counter = 0;
	
	/**
	 * Constructor for ClientHandler.
	 * @param clientSocket The client's socket to communicate with.
	 * @param engine The GameEngine of the server, which processes all commands.
	 * @param p The player object corresponding to the handled Client.
	 */
	public ClientHandler(Socket clientSocket, GameEngine engine, Player p) {
		this.clientSocket = clientSocket;
		this.engine = engine;
		this.p = p;
		
		thread = new Thread(this);
		
		try {
			OutputStream os = this.clientSocket.getOutputStream();
			os.flush();
			sendStream = new ObjectOutputStream(os);
			sendStream.flush();
			InputStream is = this.clientSocket.getInputStream();
			recvStream = new ObjectInputStream(is);
			thread.start();
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
		
		// Sends initial game state for the newly connected client to display in Client program GUI.
		sendResponse("Initial game state");
	}
	
	/**
	 * The continuous loop handling sending 
	 * of responses and listening of client requests.
	 */
	public void run() {
		while (!Thread.interrupted()) {
			if (p.getIsDead()) {
				break;
			}
			try {
				System.out.println("Waiting for command from a client...");
				String cmdLine = (String)recvStream.readObject();
				System.out.println("Received command from a Client");
				String retMsg = engine.interpretCommand(cmdLine, p);
				if (retMsg == null)
					retMsg = "Command successful";
				
				sendResponse(retMsg);
				counter = 0;
			} catch (EOFException e) {
				System.err.println(e.getMessage());
				counter++;
			} catch (ClassNotFoundException e) {
				System.err.println(e.getMessage());
			} catch (IOException e) {
				counter++;
				System.err.println(e.getMessage());
			}
			if (counter > 3)
				break;
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
		engine.removePlayer(p);
	}
	
	/**
	 * Sends response back to client.
	 * @param respMsg A message to return to the client, in addition to the Player object.
	 */
	public void sendResponse(String respMsg) {
		try {
			sendStream.writeObject(p);
			sendStream.flush();
			sendStream.reset();
			System.out.println("Transmitted response back to a Client");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
