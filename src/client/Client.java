package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.List;
import java.util.Observable;

import server.Room;
import server.Player;

/**
 * Interface between players and the server, passes updated information returned from
 * the server back to the calling gui through setChanged() and notifyObservers() methods
 * of the Observable base class.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Client extends Observable implements Runnable {
	private DatagramSocket clientSocket;
	private List<Player> players = null;
	private Room currentRoom;
	private Thread thread;
	private Socket client;
	
	private OutputStream outToServer;
	private ObjectOutputStream out;
	
	private InputStream inFromServer;
	private ObjectInputStream in;
	
	private static int PORT = 8989;
	
	/**
	 * Constructor for Client.
	 */
	public Client() {
		thread = new Thread(this);
		thread.start();
		
		
	}
	
	/**
	 * The continuous loop handling sending of input commands as well as listening
	 * for response from server program. 
	 */
	public void run() {
		try {
			client = new Socket("localhost", PORT);
			
			outToServer = client.getOutputStream();
	        out = new ObjectOutputStream(outToServer);
	
			inFromServer = client.getInputStream();
	        in = new ObjectInputStream(inFromServer);
	        
	        
			
		} catch (IOException e){
			e.printStackTrace();
		}	
	}
	
	/**
	 * Sends user-input command to server for processing.
	 * @param cmdLine The full command, including option/parameter, input by user.
	 * @throws IOException 
	 */
	public void sendCommand(String command) throws IOException {
		out.writeObject(command);
	}
	
	/**
	 * Blocks until response is received from server, then calls the gui's update method
	 * if state changed for this user's player object as a consequence of input command.
	 * @throws IOException 
	 */
	private void receiveResponse(String command) throws IOException {
		while(!command.equals("END")){
			command = in.readUTF();
		}
	}
	
	
}
