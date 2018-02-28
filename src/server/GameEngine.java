package server;

import java.util.ArrayList;
import java.util.List;

import server.commands.Command;
import server.entities.Player;

/**
 * This class is part of the "World of Zuul" application. "World of Zuul" is a
 * very simple, text based adventure game.
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
		// create the rooms
		Room outside = new Room("In a forest, the path home lies behind you", "res/rooms/outside_NEW.png", 1);
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
		Room entrance = new Room("You stand in front of an ominous ruin, there is sure to be treasures inside!", "res/rooms/outside_NS.png",
				1);
		rooms.add(entrance);

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

		entrance.setExit("south", outside16);
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
