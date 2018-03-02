package server;

import java.util.ArrayList;
import java.util.List;

import server.commands.Command;
import server.entities.Player;

/**
 * 
 * This class creates all rooms, creates the parser and starts the game. It also
 * evaluates and executes the commands that the parser returns.
 * 
 * @author Christer Sonesson
 * @version 2018-02-27
 */
public class GameEngine {
	private Parser parser;
	private List<Player> players;

	private static List<Room> rooms;

	/**
	 * Constructor for objects of class GameEngine
	 */
	public GameEngine() {
		parser = Parser.getParser();
		players = new ArrayList<>();
		rooms = new ArrayList<>();

		createRooms();
	}

	/**
	 * Create all the rooms and link their exits together.
	 */
	private void createRooms() {
		// create the rooms for level 1
		Room outside = new Room("In a forest, the path home lies behind you", "res/rooms/outside_NEW.png", 0);
		rooms.add(outside);
		Room outside1 = new Room("In a forest, a patch of white flowers are growing on the ground", "res/rooms/outside_NE.png", 1);
		rooms.add(outside1);
		Room outside2 = new Room("In a forest, it is quiet and still", "res/rooms/outside_NW.png", 1);
		rooms.add(outside2);
		Room outside3 = new Room("In a forest, you hear the gentle sound of a stream to the east", "res/rooms/outside_NES.png", 1);
		rooms.add(outside3);
		Room outside4 = new Room("In a forest, you see a small stream snaking through the forest", "res/rooms/outside_ESW.png", 1);
		rooms.add(outside4);
		Room outside5 = new Room("In a forest, you hear the gentle sound of a stream to the west", "res/rooms/outside_NSW.png", 1);
		rooms.add(outside5);
		Room outside6 = new Room("In a forest, you hear birds singing distantly", "res/rooms/outside_NS.png", 1);
		rooms.add(outside6);
		Room outside7 = new Room("In a forest, the air is calm", "res/rooms/outside_NES.png", 1);
		rooms.add(outside7);
		Room outside8 = new Room("In a forest, there is a slight breeze", "res/rooms/outside_NW.png", 1);
		rooms.add(outside8);
		Room outside9 = new Room("In a forest, you hear the cawing of a crow", "res/rooms/outside_E.png", 1);
		rooms.add(outside9);
		Room outside10 = new Room("In a forest, you hear the faint sound of a crow", "res/rooms/outside_NSW.png", 1);
		rooms.add(outside10);
		Room outside11 = new Room("In a forest, there are a few clouds overhead", "res/rooms/outside_NES.png", 1);
		rooms.add(outside11);
		Room outside12 = new Room("In a forest, you think the wind is picking up", "res/rooms/outside_SW.png", 1);
		rooms.add(outside12);
		Room outside13 = new Room("In a forest, you think the wind is picking up", "res/rooms/outside_ES.png", 1);
		rooms.add(outside13);
		Room outside14 = new Room("In a forest, there's a worn path to the north", "res/rooms/outside_NEW.png", 1);
		rooms.add(outside14);
		Room outside15 = new Room("In a forest, you spot a path leading westwards", "res/rooms/outside_SW.png", 1);
		rooms.add(outside15);
		Room outside16 = new Room("In a forest, there's a structure to the north", "res/rooms/outside_NS.png", 1);
		rooms.add(outside16);
		Room entrance = new Room("In a forest, You stand in front of an ominous ruin, there is sure to be treasures inside!", "res/rooms/entrance.png",
				1);
		rooms.add(entrance);
		
		// create the rooms for level 2 
		Room dungeon1 = new Room("In a dungeon, The entrance of the foreboding ruins...", "res/rooms/dungeon_NS.png", 2);
		rooms.add(dungeon1);
		
		Room dungeon2 = new Room("In a dungeon, The floor is littered with refuse...", "res/rooms/dungeon_E.png", 2);
		rooms.add(dungeon2);
		
		Room dungeon3 = new Room("In a dungeon, There is a stench coming from the west", "res/rooms/dungeon_EW.png", 2);
		rooms.add(dungeon3);
		
		Room dungeon4 = new Room("In a dungeon, There is a draft from the south", "res/rooms/dungeon_ESW.png", 2);
		rooms.add(dungeon4);
		
		Room dungeon5 = new Room("In a dungeon, It is slightly chilly here", "res/rooms/dungeon_NEW.png", 2);
		rooms.add(dungeon5);
		
		Room dungeon6 = new Room("In a dungeon, The air is damp", "res/rooms/dungeon_NW.png", 2);
		rooms.add(dungeon6);
		
		Room dungeon7 = new Room("In a dungeon, The cobwebs are thick in this room", "res/rooms/dungeon_ES.png", 2);
		rooms.add(dungeon7);
		
		Room dungeon8 = new Room("In a dungeon, The air is stale here", "res/rooms/dungeon_NESW.png", 2);
		rooms.add(dungeon8);
		
		Room dungeon9 = new Room("In a dungeon, There is nothing of interest here", "res/rooms/dungeon_NW.png", 2);
		rooms.add(dungeon9);
		
		Room dungeon10 = new Room("In a dungeon, Animal bones litter the floor", "res/rooms/dungeon_NS.png", 2);
		rooms.add(dungeon10);
		
		Room dungeon11 = new Room("In a dungeon, There are scratches on the walls", "res/rooms/dungeon_NS.png", 2);
		rooms.add(dungeon11);
		
		Room dungeon12 = new Room("In a dungeon, There are wooden tables here", "res/rooms/dungeon_NE.png", 2);
		rooms.add(dungeon12);
		
		Room dungeon13 = new Room("In a dungeon, There are kettles and cleavers here", "res/rooms/dungeon_NESW.png", 2);
		rooms.add(dungeon13);
		
		Room dungeon14 = new Room("In a dungeon, There are empty weapon racks here", "res/rooms/dungeon_SW.png", 2);
		rooms.add(dungeon14);
		
		Room dungeon15 = new Room("In a dungeon, There are empty armor racks here", "res/rooms/dungeon_NES.png", 2);
		rooms.add(dungeon15);
		
		Room dungeon16 = new Room("In a dungeon, Theres a primitive forge here", "res/rooms/dungeon_SW.png", 2);
		rooms.add(dungeon16);
		
		Room dungeon17 = new Room("In a dungeon, You've found some stairs leading further down...", "res/rooms/dungeon_down.png", 2);
		rooms.add(dungeon17);
		
		// create the rooms for level 3
		
		Room dungeon18 = new Room("In a dungeon, There are stairs leading up to the south", "res/rooms/dungeon_ESW.png", 3);
		rooms.add(dungeon18);
		
		Room dungeon19 = new Room("In a dungeon, It is gloomy here...", "res/rooms/dungeon_NEW.png", 3);
		rooms.add(dungeon19);
		
		Room dungeon20 = new Room("In a dungeon, This room is very dusty", "res/rooms/dungeon_NW.png", 3);
		rooms.add(dungeon20);
		
		Room dungeon21 = new Room("In a dungeon, There are tapestries on the walls", "res/rooms/dungeon_NE.png", 3);
		rooms.add(dungeon21);
		
		Room dungeon22 = new Room("In a dungeon, There are several wooden chairs here", "res/rooms/dungeon_NEW.png", 3);
		rooms.add(dungeon22);
		
		Room dungeon23 = new Room("In a dungeon, Theres a primitive brewery here", "res/rooms/dungeon_NES.png", 3);
		rooms.add(dungeon23);
		
		Room dungeon24 = new Room("In a dungeon, Theres spilt beer on the floor here", "res/rooms/dungeon_SW.png", 3);
		rooms.add(dungeon24);
		
		Room dungeon25 = new Room("In a dungeon, The walls are a bit cracked", "res/rooms/dungeon_ES.png", 3);
		rooms.add(dungeon25);
		
		Room dungeon26 = new Room("In a dungeon, Some roots have broken through the wall", "res/rooms/dungeon_NSW.png", 3);
		rooms.add(dungeon26);
		
		Room dungeon27 = new Room("In a dungeon, There's some tables full of mugs here", "res/rooms/dungeon_ES.png", 3);
		rooms.add(dungeon27);
		
		Room dungeon28 = new Room("In a dungeon, There's human bones on the floor", "res/rooms/dungeon_EW.png", 3);
		rooms.add(dungeon28);
		
		Room dungeon29 = new Room("In a dungeon, You feel a sense of foreboding danger from the north", "res/rooms/dungeon_NEW.png", 3);
		rooms.add(dungeon29);
		
		Room dungeon30 = new Room("In a dungeon, There's some blood on the floor", "res/rooms/dungeon_EW.png", 3);
		rooms.add(dungeon30);
		
		Room dungeon31 = new Room("In a dungeon, There seems to be a makeshift fighting area here", "res/rooms/dungeon_SW.png", 3);
		rooms.add(dungeon31);
		
		Room dungeon32 = new Room("In a dungeon, This is it... you've encountered the leader of the monster horde, good luck", "res/rooms/dungeon_S.png", 4);
		rooms.add(dungeon32);
		
		
		outside.setExit("north", outside4);
		outside.setExit("east", outside2);
		outside.setExit("west", outside1);

		outside1.setExit("north", outside3);
		outside1.setExit("east", outside);

		outside2.setExit("north", outside5);
		outside2.setExit("west", outside);

		outside3.setExit("north", outside6);
		outside3.setExit("east", outside4);
		outside3.setExit("south", outside1);

		outside4.setExit("east", outside5);
		outside4.setExit("south", outside);
		outside4.setExit("west", outside3);

		outside5.setExit("north", outside7);
		outside5.setExit("south", outside2);
		outside5.setExit("west", outside4);

		outside6.setExit("north", outside10);
		outside6.setExit("south", outside3);

		outside7.setExit("north", outside11);
		outside7.setExit("east", outside8);
		outside7.setExit("south", outside5);

		outside8.setExit("north", outside12);
		outside8.setExit("west", outside7);

		outside9.setExit("east", outside10);

		outside10.setExit("north", outside13);
		outside10.setExit("south", outside6);
		outside10.setExit("west", outside9);

		outside11.setExit("north", outside15);
		outside11.setExit("east", outside12);
		outside11.setExit("south", outside7);

		outside12.setExit("west", outside11);
		outside12.setExit("south", outside8);

		outside13.setExit("east", outside14);
		outside13.setExit("south", outside10);

		outside14.setExit("north", outside16);
		outside14.setExit("east", outside15);
		outside14.setExit("west", outside13);

		outside15.setExit("south", outside11);
		outside15.setExit("west", outside14);

		outside16.setExit("north", entrance);
		outside16.setExit("south", outside14);

		entrance.setExit("north", dungeon1);
		entrance.setExit("south", outside16);
		
		dungeon1.setExit("north", dungeon4);
		dungeon1.setExit("south", entrance);
		
		dungeon2.setExit("east", dungeon3);
		
		dungeon3.setExit("west", dungeon2);
		dungeon3.setExit("east", dungeon4);
		
		dungeon4.setExit("east", dungeon5);
		dungeon4.setExit("south", dungeon1);
		dungeon4.setExit("west", dungeon3);
		
		dungeon5.setExit("north", dungeon7);
		dungeon5.setExit("east", dungeon6);
		dungeon5.setExit("west", dungeon4);
		
		dungeon6.setExit("north", dungeon8);
		dungeon6.setExit("west", dungeon5);
		
		dungeon7.setExit("east", dungeon8);
		dungeon7.setExit("south", dungeon5);
		
		dungeon8.setExit("north", dungeon10);
		dungeon8.setExit("east", dungeon9);
		dungeon8.setExit("south", dungeon6);
		dungeon8.setExit("west", dungeon7);
		
		dungeon9.setExit("north", dungeon11);
		dungeon9.setExit("west", dungeon8);
		
		dungeon10.setExit("north", dungeon13);
		dungeon10.setExit("south", dungeon8);
		
		dungeon11.setExit("north", dungeon14);
		dungeon11.setExit("south", dungeon9);
		
		dungeon12.setExit("north", dungeon15);
		dungeon12.setExit("east", dungeon13);
		
		dungeon13.setExit("north", dungeon16);
		dungeon13.setExit("east", dungeon14);
		dungeon13.setExit("south", dungeon10);
		dungeon13.setExit("west", dungeon12);
		
		dungeon14.setExit("south", dungeon11);
		dungeon14.setExit("west", dungeon13);
		
		dungeon15.setExit("north", dungeon17);
		dungeon15.setExit("east", dungeon16);
		dungeon15.setExit("south", dungeon12);
		
		dungeon16.setExit("south", dungeon13);
		dungeon16.setExit("west", dungeon15);
		
		dungeon17.setExit("north", dungeon18);
		dungeon17.setExit("south", dungeon15);
		
		dungeon18.setExit("east", dungeon19);
		dungeon18.setExit("south", dungeon17);
		dungeon18.setExit("west", dungeon22);
		
		dungeon19.setExit("north", dungeon25);
		dungeon19.setExit("east", dungeon20);
		dungeon19.setExit("west", dungeon18);
		
		dungeon20.setExit("north", dungeon26);
		dungeon20.setExit("west", dungeon19);
		
		dungeon21.setExit("north", dungeon23);
		dungeon21.setExit("east", dungeon22);
		
		dungeon22.setExit("north", dungeon24);
		dungeon22.setExit("east", dungeon18);
		dungeon22.setExit("west", dungeon21);
		
		dungeon23.setExit("north", dungeon27);
		dungeon23.setExit("east", dungeon24);
		dungeon23.setExit("south", dungeon21);
		
		dungeon24.setExit("south", dungeon22);
		dungeon24.setExit("west", dungeon23);
		
		dungeon25.setExit("east", dungeon26);
		dungeon25.setExit("south", dungeon19);
		
		dungeon26.setExit("north", dungeon31);
		dungeon26.setExit("south", dungeon20);
		dungeon26.setExit("west", dungeon25);
		
		dungeon27.setExit("east", dungeon28);
		dungeon27.setExit("south", dungeon23);
		
		dungeon28.setExit("east", dungeon29);
		dungeon28.setExit("west", dungeon27);
		
		dungeon29.setExit("north", dungeon32);
		dungeon29.setExit("east", dungeon30);
		dungeon29.setExit("west", dungeon28);
		
		dungeon30.setExit("east", dungeon31);
		dungeon30.setExit("west", dungeon29);
		
		dungeon31.setExit("south", dungeon26);
		dungeon31.setExit("west", dungeon30);
		
		
	}

	/**
	 * Given a command, process (that is: execute) the command. 
	 * 
	 * @param commandLine
	 *            The command to process.
	 * @param p
	 *            The Player object to execute the command for.
	 * @return True if the command changed some state of the game, false
	 *         otherwise.
	 */
	public synchronized boolean interpretCommand(String commandLine, Player p) {
		Command command = parser.getCommand(commandLine);

		if (command == null) {
			p.setCmdReturnMsg("Command was not recognized, type 'help' for help.");
			return false;
		}

		boolean ret = command.execute(p);

		if (ret == false) {
			p.setCmdReturnMsg("Command failed, type 'help' for help.");
			return false;
		}
		return true;
	}

	/**
	 * Returns the players currently connected to this server.
	 * 
	 * @return The registered/connected players on this server.
	 */
	public synchronized List<Player> getPlayers() {
		return players;
	}

	/**
	 * Returns initial room for all newly created players.
	 * 
	 * @return The starting room.
	 */
	public synchronized Room getStartRoom() {
		return rooms.get(0);
	}

	/**
	 * Adds a player to the game, called when a new client connects.
	 * 
	 * @param newPlayer
	 *            The new player object to add for the newly connected Client.
	 */
	public synchronized void addPlayer(Player newPlayer) {
		players.add(newPlayer);
	}

	/**
	 * Removes a player object from the game, called when a client disconnects.
	 * 
	 * @param newPlayer
	 *            The player object to remove; associated with the disconnecting
	 *            Client.
	 */
	public synchronized void removePlayer(Player disconnectedPlayer) {
		if (disconnectedPlayer != null) {
			disconnectedPlayer.getRoom().removePlayer(disconnectedPlayer);
			players.remove(disconnectedPlayer);
		}
	}

	/**
	 * Finds the current version of the room with roomDescription as its short
	 * description. Used for loading saved rooms.
	 * 
	 * @param roomDescription
	 *            The short room description of the searched room.
	 */
	public static synchronized Room getRoom(String roomDescription) {
		for (Room r : rooms) {
			if (r.getShortDescription().equals(roomDescription))
				return r;
		}

		return null;
	}
}
