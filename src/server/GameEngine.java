package server;

import java.util.ArrayList;
import java.util.List;

/**
 *  This class is part of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.
 * 
 *  This class creates all rooms, creates the parser and starts
 *  the game.  It also evaluates and executes the commands that 
 *  the parser returns.
 * 
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class GameEngine
{
    private Parser parser;
    private List<Player> players;
    private Room outside, frozen, abandoned, furnished, occult, warped;

    /**
     * Constructor for objects of class GameEngine
     */
    public GameEngine()
    {
        parser = Parser.getParser();
        players = new ArrayList<>();
        createRooms();
    }
    
    /**
     * Returns the players currently connected to this server.
     * @return The registered/connected players on this server.
     */
    public List<Player> getPlayers() {
    	return players;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    { 
        // create the rooms
        outside = new Room("outside the Main Entrance", "outside.gif");
        frozen = new Room("in a Frozen Room", "throne-room.gif");
        abandoned = new Room("in an Abandoned Room", "courtyard.gif");
        furnished = new Room("in a Furnished Room", "hallway.gif");
        occult = new Room("in an Occult Room", "dungeon.gif");
        warped = new Room("in a Warped Room", "stairs.gif");
        
        // initialize room exits
        outside.setExit("east", frozen);
        outside.setExit("south", abandoned);
        outside.setExit("west", furnished);
        frozen.setExit("west", outside);
        abandoned.setExit("north", outside);
        furnished.setExit("east", outside);
        occult.setExit("east", warped);
        warped.setExit("west", occult);
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command should exit the client from this game, true is returned, 
     * otherwise false is returned.
     * @param commandLine The command to process.
     * @param p The Player object to execute the command for.
     * @return An optional status/response message for requesting client.
     */
    public synchronized String interpretCommand(String commandLine, Player p) 
    {
        Command command = parser.getCommand(commandLine);
        if (command == null) {
        	return printHelp();
        }
        
        boolean ret = command.execute(p);
        
        if (ret == false) {
        	return printHelp();
        }
        
        return command.getReturnMessage();
    }

    /**
     * Returns initial room for all newly created players.
     * @return The starting room.
     */
    public Room getStartRoom() {
    	return outside;
    }
    
    /**
     * Adds a player to the game, called when a new client connects.
     * @param newPlayer The new player object to add for the newly connected Client.
     */
    public void addPlayer(Player newPlayer) {
    	players.add(newPlayer);
    }
    
    /**
     * Removes a player object from the game, called when a client disconnects.
     * @param newPlayer The player object to remove; associated with the disconnecting Client.
     */
    public void removePlayer(Player disconnectedPlayer) {
    	players.remove(disconnectedPlayer);
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     * @return The help string.
     */
	private String printHelp() 
    {
        /*gui.println("You are lost. You are alone. You wander");
        gui.println("around the Maze.\n");
        gui.print("Your command words are: " + parser.showCommands());*/
		return "You are lost. You are alone. You wander\naround the Maze.\n" + 
		"Your command words are: " + parser.showCommands();
    }
}
