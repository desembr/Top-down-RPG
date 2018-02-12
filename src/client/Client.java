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
	
	public Client() {
		thread = new Thread(this);
	}
	
	public void run() {
		
	}
	
	public void sendCommand(String cmdLine) {
		
	}
	
	private void receiveResponse() {
		
	}
}
