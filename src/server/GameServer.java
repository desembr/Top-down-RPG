package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class on the server-side which manages the game-logic and listens/accepts
 * client connections. Inserts a new, managed Player object for every new Client.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class GameServer extends Thread
{
	private static final int serverPort = 44120;
	
	private GameEngine engine;
	private ServerSocket serverSocket;
	
	private static List<ClientHandler> clientHandlers;

    /**
     * Create the game and initialize its internal map.
     */
    public GameServer() 
    {
		engine = new GameEngine();
		
		clientHandlers = new ArrayList<>();
		
		try {
			serverSocket = new ServerSocket(serverPort);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		System.out.println("Server started...listening on port " + serverPort);
		
		start();
    }
    
    /**
     * The continuous listening/responding main loop of the server program.
     */
    public void run() {
    	while (!interrupted()) {
    		try {
				Socket clientSocket = serverSocket.accept();
				System.out.println("New client connected");
				Player newPlayer = new Player(engine.getStartRoom());
				engine.addPlayer(newPlayer);
				clientHandlers.add(new ClientHandler(clientSocket, engine, newPlayer));
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
    	}
    	System.out.println("Server exiting...bye");
    	try {
    		if (serverSocket != null)
    			serverSocket.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
    }
    
    /**
     * Returns the ClientHandlers for those handlers whose player objects is currently in room r.
     * @param r The room for whom all residing players should update on state change from some Client
     * also residing in that room.
     * @return The ClientHandlers meeting the conditions mentioned above.
     */
    public static List<ClientHandler> getClientHandlers(Room r) {
    	if (r == null)
    		return null;
    	
    	List<ClientHandler> res = new ArrayList<>();
    	for (ClientHandler ch : clientHandlers) {
    		if (ch.getPlayer().getRoom() == r)
    			res.add(ch);
    	}
    	return res;
    }
    
    /**
     * Removes a ClientHandler on Client-disconnect.
     * @param ch The ClientHandler to remove.
     */
    public static void removeClientHandler(ClientHandler ch) {
    	if (ch != null)
    		clientHandlers.remove(ch);
    }
    
    /**
     * Entry point to server program.
     */
    public static void main(String[] args) {
    	new GameServer();
    }
}
