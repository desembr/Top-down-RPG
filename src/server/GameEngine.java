package server;

import java.util.ArrayList;
import java.util.List;

import client.UserInterface;

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
    //private UserInterface gui;
    private List<Player> players;

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
     * 
     * @return The registered/connected players on this server.
     */
    public List<Player> getPlayers() {
    	return players;
    }

    /*public void setGUI(UserInterface userInterface)
    {
        gui = userInterface;
        printWelcome();
    }*/

    /**
     * Print out the opening message for the player.
     */
   /* private void printWelcome()
    {
        gui.print("\n");
        gui.println(currentRoom.getLongDescription());
        gui.println(currentRoom.showEnemiesInRoom());
        
        // För att startrummet ska få en image, kan tas bort om första rummet Är en meny 
        // och goto används internt för att komma från menyn till första rummet 
        if (currentRoom.hasImage() ) 
        {
            gui.showImage( currentRoom.getImage() ); 
        }
        
        gui.showPlayer("player_no_armor_64x64.png"); // test
    }*/

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, frozen, abandoned, furnished, occult, warped, imageTestRoom;
      
        // create the rooms
        outside = new Room("outside the Main Entrance", "outside800x600.png");
        frozen = new Room("in a Frozen Room", "dungeon_room800x600.png");
        abandoned = new Room("in an Abandoned Room", "dungeon_room800x600.png");
        furnished = new Room("in a Furnished Room", "dungeon_room800x600.png");
        occult = new Room("in an Occult Room", "dungeon_room800x600.png");
        warped = new Room("in a Warped Room", "dungeon_room800x600.png");
        
        imageTestRoom = new Room("There should be an image here", "dungeon_room800x600.png"); // test
        
        // initialise room exits
        outside.setExit("east", frozen);
        outside.setExit("south", abandoned);
        outside.setExit("west", furnished);

        //frozen.setExit("west", outside);

        abandoned.setExit("south", imageTestRoom); // test
        //abandoned.setExit("north", outside); // test

        //furnished.setExit("north", outside);
        occult.setExit("east", warped);

        //warped.setExit("west", occult);
        
        //imageTestRoom.setExit("north", abandoned); // test

        //currentRoom = outside;  // start game outside
        
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     * @param commandLine The command to process.
     * @return A list of players to send back to the sender of this command.
     */
    public String interpretCommand(String commandLine) 
    {
        //gui.println(commandLine);
        Command command = parser.getCommand(commandLine);
        //TODO:execute on the sending client's corresponding player object, stored in players.
        boolean ret = command.execute(null);
        if (ret == false) {
        	return printHelp();
        }
        
        return command.getReturnMessage();
        /*if(command.isUnknown()) {
            gui.println("I don't know what you mean...");
            return;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if (commandWord.equals("quit")) {
            if(command.hasSecondWord())
                gui.println("Quit what?");
            else
                endGame();
        }*/
    }

    // implementations of user commands:

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

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    /*private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            gui.println("Go where?");
            return;
        }
    
        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null)
            gui.println("There is no door!");
        else {
            currentRoom = nextRoom;
            gui.println(currentRoom.getLongDescription());
            gui.println(currentRoom.showEnemiesInRoom());
            
            if (nextRoom.hasImage() ) // visar en bild om nästa rum har en 
	        {
	            gui.showImage( nextRoom.getImage() ); 
	        }
        
        }        
    }

    private void endGame()
    {
        gui.println("Thank you for playing.  Good bye.");
        gui.enable(false);
    }*/

}
