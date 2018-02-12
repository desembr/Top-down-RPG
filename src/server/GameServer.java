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
     * Send responses to communicating clients.
     * @param players The list of players to send to a client.
     */
    private void sendResponse(List<Player> players) {
    	//TODO: implement communication with clients.
    }
    
    public void run() {
    	while (!interrupted()) {
    		String cmdLine = receiveCommand();
    		List<Player> players = engine.interpretCommand(cmdLine);
    		sendResponse(players);
    	}
    }
    
    public static void main(String[] args) {
    	new GameServer();
    }
}
