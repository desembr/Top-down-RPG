package server; 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Room implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String description, imageName;
    private HashMap<String,Room> exits;        // stores exits of this room.
    private ArrayList<Enemy> enemiesInRoom;
    private ArrayList<Player> playersInRoom;
    private ArrayList<Item> itemsInRoom;
    
    /**
     * Create a room described "description". Initially, it has no exits.
     * description" is something like "in a kitchen" or "in an open court yard".
     * @param description The description of this room.
     * @param imageName The imageFilePath of this room.
     */
    public Room(String description, String imageName, int roomLevel)
    {
        this.description = description;
        this.imageName = imageName; 
       
        exits = new HashMap<String,Room>();
        enemiesInRoom = new ArrayList<Enemy>();
        playersInRoom = new ArrayList<Player>();
        itemsInRoom = new ArrayList<Item>();
       
        
        // Add some enemies and items to this room.
        try {
			Thread.sleep(33); // för att random seeden från millis ska bli bättre
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        Random randomiser = new Random( System.currentTimeMillis() ); // time used to get a semi-random seed
        int zero_to_five = randomiser.nextInt(6); // random number between 0-5
        for (int i = 0; i < zero_to_five; i++) {
        	if(roomLevel == 1)
        	{
        		int random = randomiser.nextInt(2);  
        		if (random == 0)
        		{
        			enemiesInRoom.add(new Goblin());
        			if (randomiser.nextInt(4) == 0)
        				itemsInRoom.add(new Apple());
        		}
        		else
        		{
        			int randomStats = randomiser.nextInt(5);
        			if (randomiser.nextInt(3) == 0) {
        				itemsInRoom.add(new Pie());
        			}
        			else if (randomiser.nextInt(3) == 0) {
        				itemsInRoom.add(new Sword(5 + randomStats, 2 + randomStats));
        			}
        			else if (randomiser.nextInt(3) == 0) {
        				itemsInRoom.add(new Shield(2 + randomStats, 12 + randomStats));
        			}
        			enemiesInRoom.add(new Gremlin());
        		}
        	}
        }
    }
    
    /**
     * Returns info about enemies in this room.
     * @return Contained enemies description.
     */
    public String showEnemiesInRoom(){
        if(enemiesInRoom.isEmpty()){
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
     * @return Contained players description.
     */
    private String showPlayersInRoom(){
        if(playersInRoom.size() == 1){
            return "There are currently no other players in here.";
        }
        String returnString = "Players:";
        for (Player p : playersInRoom) {
        	returnString += " " + p.getName();
        }
        return returnString;
    }
    
    /**
     * Returns info about items in this room.
     * @return Contained items description.
     */
    private String showItemsInRoom(){
        if(itemsInRoom.isEmpty()){
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
     * @param direction The direction.
     * @param neighbor
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * Return the description of the room (the one that was defined in the
     * constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a long description of this room, in the form:
     *     You are in the kitchen.
     *     Exits: north west
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString() + 
        		".\n" + showEnemiesInRoom() + ".\n" + showItemsInRoom() + 
        		".\n" + showPlayersInRoom();
    }
    
    /**
     * Return a long description of this room, in the form:
     *     You see in the kitchen.
     *     Exits: north west
     */
    public String getPeekDescription()
    {
        return "You see " + description + ".\n" + getExitString() + 
        		".\n" + showEnemiesInRoom() + ".\n" + showItemsInRoom() + 
        		".\n" + showPlayersInRoom();
    }
    

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(Iterator<String> iter = keys.iterator(); iter.hasNext(); )
            returnString += " " + iter.next();
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Returns enemies contained within this room.
     * @return The enemies contained in this room.
     */
    public List<Enemy> getEnemies()
    {
        return enemiesInRoom; 
    }
    
    /**
     * Returns players contained within this room.
     * @return The players contained in this room.
     */
    public List<Player> getPlayers()
    {
        return playersInRoom; 
    }
    
    /**
     * Returns items contained within this room.
     * @return The items contained in this room.
     */
    public List<Item> getItems()
    {
        return itemsInRoom; 
    }
    
    /** Attempts to pick up (remove) an item from this room.
     * @param itemName The name of the item to pick up.
     * @return The item picked up if itemIndex is valid, else null.
     */
    public Item pickItem(String itemName)
    {
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
     * @return The imageFilePath of this room.
     */
    public String getImage()
    {
        return imageName; 
    }

    /**
     * Adds a player currently residing in this room.
     * @param player The player to add.
     */
	public synchronized void addPlayer(Player player) {
		if (!playersInRoom.contains(player))
			playersInRoom.add(player);
	}

	/**
     * Adds an item to this room.
     * @param item The item to add.
     */
	public void addItem(Item item) {
		itemsInRoom.add(item);
	}

	/**
     * Removes a player from this room
     * @param player The player to remove.
     */
	public void removePlayer(Player player) {
		playersInRoom.remove(player);
	}
}

