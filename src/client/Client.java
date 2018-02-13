package client;

import java.net.DatagramSocket;
import java.util.List;
import java.util.Observable;

import server.Room;
import server.Player;

/**
 * Interface between players and the server.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Client extends Observable implements Runnable {
	private DatagramSocket clientSocket;
	private List<Player> players = null;
	private Room currentRoom;
	private Thread thread;
	
	/**
	 * Constructor for Client.
	 */
	public Client() {
		thread = new Thread(this);
	}
	
	/**
	 * The continuous loop handling sending of input commands as well as listening
	 * for response from server program. 
	 */
	public void run() {
		
	}
	
	/**
	 * Sends user-input command to server for processing.
	 * @param cmdLine The full command, including option/parameter, input by user.
	 */
	public void sendCommand(String cmdLine) {
		
	}
	
	/**
	 * Blocks until response is received from server, then calls the gui's update method
	 * if state changed for this user's player object as a consequence of input command.
	 */
	private void receiveResponse() {
		
	}
}
