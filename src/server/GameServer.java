package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

    /**
     * Create the game and initialize its internal map.
     */
    public GameServer() 
    {
		engine = new GameEngine();
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
				new ClientHandler(clientSocket, engine, newPlayer);
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
     * Entry point to server program.
     */
    public static void main(String[] args) {
    	new GameServer();
    }
}
