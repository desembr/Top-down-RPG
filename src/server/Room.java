package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import server.entities.Boss;
import server.entities.Enemy;
import server.entities.Entity;
import server.entities.Goblin;
import server.entities.Gremlin;
import server.entities.Ogre;
import server.entities.Orc;
import server.entities.Player;
import server.items.Apple;
import server.items.ArmArmor;
import server.items.ChestArmor;
import server.items.Helmet;
import server.items.Item;
import server.items.LegArmor;
import server.items.Pie;
import server.items.Shield;
import server.items.Sword;

/*
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author Christer Sonesson
 * @version 2018-02-25
 */
public class Room implements Serializable {
	private static final long serialVersionUID = 1L;

	private String description, imageName;
	private HashMap<String, Room> exits; // stores exits of this room.
	private ArrayList<Enemy> enemiesInRoom;
	private ArrayList<Player> playersInRoom;
	private ArrayList<Item> itemsInRoom;

	/**
	 * Create a room described "description". Initially, it has no exits.
	 * description" is something like "in a kitchen" or "in an open court yard".
	 * 
	 * @param description
	 *            The description of this room.
	 * @param imageName
	 *            The imageFilePath of this room.
	 * @param roomLevel
	 *            The level this room belongs to.
	 */
	public Room(String description, String imageName, int roomLevel) {
		this.description = description;
		this.imageName = imageName;

		exits = new HashMap<String, Room>();
		enemiesInRoom = new ArrayList<Enemy>();
		playersInRoom = new ArrayList<Player>();
		itemsInRoom = new ArrayList<Item>();

		// Add some enemies and items to this room.
		try {
			Thread.sleep(33); // To get better seed
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Random randomiser = new Random(System.currentTimeMillis()); // time used
																	// to get a
																	// semi-random
																	// seed
		
		if (roomLevel == 0) // this is the starting room, give the player two apples to start him on his journey 
		{
			itemsInRoom.add(new Apple());
			itemsInRoom.add(new Apple());
			
		}
		
		else if (roomLevel == 4) // this is the final room,no randomization needed
		{
			itemsInRoom.add(new Pie());
			enemiesInRoom.add(new Boss());
		}
		
		int zero_to_five = randomiser.nextInt(6); // random number between 0-5
		for (int i = 0; i < zero_to_five; i++) // loop for adding monsters and items
		{
				
			if (roomLevel == 1) // set when creating a room
			{
				int random = randomiser.nextInt(4); // random number between 0-3
				if (random == 0) // 25% chance, add monster
				{
					enemiesInRoom.add(new Gremlin());
					
					if (randomiser.nextInt(4) == 0) // 25% chance, bonus health item
					{
						itemsInRoom.add(new Apple());
					}
				} 
				else if (random == 1) // 25% chance, add monster
				{
					enemiesInRoom.add(new Goblin());
					
					if (randomiser.nextInt(5) == 0) // 20 % chance, bonus health item
					{
						itemsInRoom.add(new Pie());
					}
				} 
				
					// if random == 2 then do nothing, no risk or monster nor chance of bonus items, 25% chance
				
				else // // 25% chance, no monster + roll for items, possibility of weapons and armor
				{
					int randomStats = randomiser.nextInt(5); // 0-4
					int randomItem = randomiser.nextInt(8);  // 0-7, 13% risk of no item
					if (randomItem == 0) { // 13% chance
						itemsInRoom.add(new Pie());
					} else if (randomItem == 1) { // 13% chance
						itemsInRoom.add(new Sword(5 + randomStats, 0));
					} else if (randomItem == 2) { // 13% chance
						itemsInRoom.add(new Shield(0, 12 + randomStats));
					}
					else if (randomItem == 3) { // 13% chance
						randomItem = randomiser.nextInt(4); // make it a bit more difficult to find these
						if (randomItem == 0)
						itemsInRoom.add(new Helmet(0, 2, 10 ));
					}
					else if (randomItem == 4) { // 13% chance
						randomItem = randomiser.nextInt(4); // make it a bit more difficult to find these
						if (randomItem == 0)
						itemsInRoom.add(new ChestArmor(0, 2, 10 ));
					}
					else if (randomItem == 5) { // 13% chance
						randomItem = randomiser.nextInt(4); // make it a bit more difficult to find these
						if (randomItem == 0)
						itemsInRoom.add(new ArmArmor(0, 2, 10 ));
					}
					else if (randomItem == 6) { // 13% chance
						randomItem = randomiser.nextInt(4); // make it a bit more difficult to find these
						if (randomItem == 0)
						itemsInRoom.add(new LegArmor(0, 2, 10 ));
					}
					
					
				}
			}
				
			else if (roomLevel == 2) // set when creating a room
				{
					int random = randomiser.nextInt(5); // random number between 0-4
					if (random == 0) // 20% chance, add monster
					{
						enemiesInRoom.add(new Gremlin());
						
						if (randomiser.nextInt(4) == 0) // 25% chance, bonus health item
						{
							itemsInRoom.add(new Apple());
						}
					} 
					else if (random == 1) // 20% chance, add monster
					{
						enemiesInRoom.add(new Goblin());
						
						if (randomiser.nextInt(5) == 0) // 20 % chance, bonus health item
						{
							itemsInRoom.add(new Pie());
						}
					} 
					else if (random == 2) // 20% chance, add monster
					{
						enemiesInRoom.add(new Orc());
						
						if (randomiser.nextInt(4) == 0) // 25% chance, bonus health item
						{
							itemsInRoom.add(new Apple());
						}
					} 
					
					// if random == 3 then do nothing, no risk or monster nor chance of bonus items, 20% chance
					
					else // // 20% chance, no monster +  roll for items, possibility of weapons and armor
					{
						int randomStats = randomiser.nextInt(9); // 0-8, possibility of finding better items than last level
						int randomItem = randomiser.nextInt(8);  // 0-7, 13% risk of no item
						if (randomItem == 0) { // 13% chance
							itemsInRoom.add(new Pie());
						} else if (randomItem == 1) { // 13% chance
							itemsInRoom.add(new Sword(5 + randomStats, 0));
						} else if (randomItem == 2) { // 13% chance
							itemsInRoom.add(new Shield(0, 12 + randomStats));
						}
						else if (randomItem == 3) { // 13% chance
							randomItem = randomiser.nextInt(4); // make it a bit more difficult to find these
							if (randomItem == 0)
							itemsInRoom.add(new Helmet(0, 2, 10 ));
						}
						else if (randomItem == 4) { // 13% chance
							randomItem = randomiser.nextInt(4); // make it a bit more difficult to find these
							if (randomItem == 0)
							itemsInRoom.add(new ChestArmor(0, 2, 10 ));
						}
						else if (randomItem == 5) { // 13% chance
							randomItem = randomiser.nextInt(4); // make it a bit more difficult to find these
							if (randomItem == 0)
							itemsInRoom.add(new ArmArmor(0, 2, 10 ));
						}
						else if (randomItem == 6) { // 13% chance
							randomItem = randomiser.nextInt(4); // make it a bit more difficult to find these
							if (randomItem == 0)
							itemsInRoom.add(new LegArmor(0, 2, 10 ));
						}
						
					}
			    }
				
			else if (roomLevel == 3) // set when creating a room
				{
					int random = randomiser.nextInt(6); // random number between 0-5
					if (random == 0) // 17% chance, add monster
					{
						enemiesInRoom.add(new Gremlin());
						
						if (randomiser.nextInt(4) == 0) // 25% chance, bonus health item
						{
							itemsInRoom.add(new Apple());
						}
					} 
					else if (random == 1) // 17% chance, add monster
					{
						enemiesInRoom.add(new Goblin());
						
						if (randomiser.nextInt(5) == 0) // 20 % chance, bonus health item
						{
							itemsInRoom.add(new Pie());
						}
					} 
					else if (random == 2) // 17% chance, add monster
					{
						enemiesInRoom.add(new Orc());
						
						if (randomiser.nextInt(4) == 0) // 25% chance, bonus health item
						{
							itemsInRoom.add(new Apple());
						}
					} 
					else if (random == 3) // 17% chance, add monster
					{
						enemiesInRoom.add(new Ogre());
						
						if (randomiser.nextInt(4) == 0) // 25% chance, bonus health item
						{
							itemsInRoom.add(new Pie());
						}
					} 
					
					// if random == 4 then do nothing, no risk or monster nor chance of bonus items, 17% chance
					
					else // // 17% chance, no monster +  roll for items, possibility of weapons and armor
					{
						int randomStats = randomiser.nextInt(13); // 0-12, possibility of finding even better items than last level 
						int randomItem = randomiser.nextInt(8);  // 0-7, 13% risk of no item
						if (randomItem == 0) { // 13% chance
							itemsInRoom.add(new Pie());
						} else if (randomItem == 1) { // 13% chance
							itemsInRoom.add(new Sword(5 + randomStats, 0));
						} else if (randomItem == 2) { // 13% chance
							itemsInRoom.add(new Shield(0, 12 + randomStats));
						}
						else if (randomItem == 3) { // 13% chance
							randomItem = randomiser.nextInt(4); // make it a bit more difficult to find these
							if (randomItem == 0)
							itemsInRoom.add(new Helmet(0, 2, 10 ));
						}
						else if (randomItem == 4) { // 13% chance
							randomItem = randomiser.nextInt(4); // make it a bit more difficult to find these
							if (randomItem == 0)
							itemsInRoom.add(new ChestArmor(0, 2, 10 ));
						}
						else if (randomItem == 5) { // 13% chance
							randomItem = randomiser.nextInt(4); // make it a bit more difficult to find these
							if (randomItem == 0)
							itemsInRoom.add(new ArmArmor(0, 2, 10 ));
						}
						else if (randomItem == 6) { // 13% chance
							randomItem = randomiser.nextInt(4); // make it a bit more difficult to find these
							if (randomItem == 0)
							itemsInRoom.add(new LegArmor(0, 2, 10 ));
						}
						
					}
			    }
				
			
		}
	}

	/**
	 * Returns info about enemies in this room.
	 * 
	 * @return Contained enemies description.
	 */
	public String showEnemiesInRoom() {
		if (enemiesInRoom.isEmpty()) {
			return "No enemies in here.";
		}
		String returnString = "Alive enemies:";
		for (Entity e : enemiesInRoom) {
			if (!e.getIsDead())
				returnString += " " + e.getName();
		}
		returnString += "\nDead enemies:";
		for (Entity e : enemiesInRoom) {
			if (e.getIsDead())
				returnString += " " + e.getName();
		}
		returnString += "\nEnemy statistics:";
		for (Entity e : enemiesInRoom) {
			if (e.getHealth() > 0)
				returnString += "\n" + e.getName() + " has " + e.getHealth() + " health left";

		}
		return returnString;
	}

	/**
	 * Returns info about players in this room.
	 * 
	 * @return Contained players description.
	 */
	public String showPlayersInRoom() {
		if (playersInRoom.isEmpty()) {
			return "There are currently no players in here.";
		}
		String returnString = "Players:";
		for (Player p : playersInRoom) {
			returnString += " " + p.getName();
		}
		return returnString;
	}

	/**
	 * Returns info about items in this room.
	 * 
	 * @return Contained items description.
	 */
	public String showItemsInRoom() {
		if (itemsInRoom.isEmpty()) {
			return "No items in here.";
		}
		String returnString = "Items:";
		for (Item i : itemsInRoom) {
			returnString += " " + i.getName();
		}
		return returnString;
	}

	/**
	 * Define an exit from this room.
	 * 
	 * @param direction
	 *            The direction.
	 * @param neighbor
	 */
	public void setExit(String direction, Room neighbor) {
		exits.put(direction, neighbor);
	}

	/**
	 * Return the description of the room (the one that was defined in the
	 * constructor).
	 * 
	 * @return A short description of this room.
	 */
	public String getShortDescription() {
		return description;
	}

	/**
	 * Return a long description of this room, in the form: You are in the
	 * kitchen. Exits: north west
	 * 
	 * @return A long description of this room.
	 */
	public String getLongDescription() {
		return "You are " + description + ".\n" + getExitString() + ".\n" + showEnemiesInRoom() + ".\n" + showItemsInRoom() + ".\n"
				+ showPlayersInRoom();
	}

	/**
	 * Return a long description of this room, in the form: You see in the
	 * kitchen. Exits: north west
	 * 
	 * @return A long description of an adjacent room.
	 */
	public String getPeekDescription() {
		return "You see " + description + ".\n" + getExitString() + ".\n" + showEnemiesInRoom() + ".\n" + showItemsInRoom() + ".\n"
				+ showPlayersInRoom();
	}

	/**
	 * Return a string describing the room's exits, for example "Exits: north
	 * west".
	 */
	private String getExitString() {
		String returnString = "Exits:";
		Set<String> keys = exits.keySet();
		for (Iterator<String> iter = keys.iterator(); iter.hasNext();)
			returnString += " " + iter.next();
		return returnString;
	}

	/**
	 * Return the room that is reached if we go from this room in direction
	 * "direction". If there is no room in that direction, return null.
	 */
	public Room getExit(String direction) {
		return exits.get(direction);
	}

	/**
	 * Returns enemies contained within this room.
	 * 
	 * @return The enemies contained in this room.
	 */
	public List<Enemy> getEnemies() {
		return enemiesInRoom;
	}

	/**
	 * Returns players contained within this room.
	 * 
	 * @return The players contained in this room.
	 */
	public List<Player> getPlayers() {
		return playersInRoom;
	}

	/**
	 * Returns items contained within this room.
	 * 
	 * @return The items contained in this room.
	 */
	public List<Item> getItems() {
		return itemsInRoom;
	}

	/**
	 * Attempts to pick up (remove) an item from this room.
	 * 
	 * @param itemName
	 *            The name of the item to pick up.
	 * @return The item picked up if itemIndex is valid, else null.
	 */
	public Item pickItem(String itemName) {
		for (int i = 0; i < itemsInRoom.size(); i++) {
			Item item = itemsInRoom.get(i);
			if (item.getName().toLowerCase().equals(itemName.toLowerCase())) {
				return itemsInRoom.remove(i);
			}
		}
		return null;
	}

	/**
	 * Returns the imageFilePath of this room, used for display on Client.
	 * 
	 * @return The imageFilePath of this room.
	 */
	public String getImage() {
		return imageName;
	}

	/**
	 * Adds a player currently residing in this room.
	 * 
	 * @param player
	 *            The player to add.
	 */
	public synchronized void addPlayer(Player player) {
		if (!playersInRoom.contains(player))
			playersInRoom.add(player);
	}

	/**
	 * Adds an item to this room.
	 * 
	 * @param item
	 *            The item to add.
	 */
	public void addItem(Item item) {
		itemsInRoom.add(item);
	}

	/**
	 * Removes a player from this room
	 * 
	 * @param player
	 *            The player to remove.
	 */
	public void removePlayer(Player player) {
		playersInRoom.remove(player);
	}
}
