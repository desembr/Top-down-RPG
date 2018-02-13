package server;

import java.net.DatagramSocket;
import java.util.List;

/**
 *  Main class on the server-side which handles the game-logic and communicates with players.
 * 
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class GameServer extends Thread
{
	//private UserInterface gui;
	private GameEngine engine;
	private DatagramSocket serverSocket;

    /**
     * Create the game and initialize its internal map.
     */
    public GameServer() 
    {
		engine = new GameEngine();
		//gui = new UserInterface(engine);
		//engine.setGUI(gui);
    }
    
    /**
     * Listen for commands from a client.
     * @return The received command to process.
     */
    private String receiveCommand() {
    	//TODO: implement communication with clients.
    	return "";
    }
    
    /**
     * Send responses to communicating clients, consisting of all connected players and possibly 
     * also a responseMessage from the executed command.
     * @param responseMessage The message returned by the input, processed command. It's null
     * if the command didn't return one, and should then not be sent back to the client/user.
     */
    private void sendResponse(String responseMessage) {
    	//TODO: implement communication with clients.
    	List<Player> players = engine.getPlayers();
    }
    
    /**
     * The continuous listening/responding main loop of the server program.
     */
    public void run() {
    	while (!interrupted()) {
    		String commandLine = receiveCommand();
    		String returnMessage = engine.interpretCommand(commandLine);
    		sendResponse(returnMessage);
    	}
    }
    
    /**
     * Entry point to server program.
     */
    public static void main(String[] args) {
    	new GameServer();
    }
}
