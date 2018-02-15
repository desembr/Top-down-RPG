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
    private List<Item> itemsInRoom;
    
    private static Random randomGen = new Random();
    
    /**
     * * Create a room described "description". Initially, it has no exits.
     * "description" is something like "in a kitchen" or "in an open court 
     * yard".
     * @param description The description of this room.
     * @param imageName The imageFilePath of this room.
     */
    public Room(String description, String imageName)
    {
        this.description = description;
        this.imageName = imageName; 
        
        exits = new HashMap<String,Room>();
        enemiesInRoom = new ArrayList<Enemy>();
        itemsInRoom = new ArrayList<Item>();
        
        // Add some enemies to this room.
        for (int i = 0; i < randomGen.nextInt(5) + 1; i++) {
        	enemiesInRoom.add(new Orc());
        }
    }
    
    /**
     * 
     * @return Contained enemies description.
     */
    private String showEnemiesInRoom(){
        if(enemiesInRoom.isEmpty()){
            return "No enemies in here.";
        }
        String returnString = "Enemies:";
        for (Entity e : enemiesInRoom) {
        	returnString += " " + e.getName();
        }
        return returnString;
    }
    
    /**
     * 
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
        		".\n" + showEnemiesInRoom() + ".\n" + showItemsInRoom();
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
        	if (item.getName().equals(itemName)) {
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
}

