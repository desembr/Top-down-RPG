package server; 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
	
	private String description;
    private HashMap<String,Room> exits;        // stores exits of this room.
    private ArrayList<Enemy> enemiesInRoom;
    private List<Item> itemsInRoom;
    
    private boolean hasImage; // boolean used in room-creation
    private String imageName; // set in room creation and passed to userInterface via GameEngine

    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "in a kitchen" or "in an open court 
     * yard".
     * @param description The description of this room.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String,Room>();
        enemiesInRoom = new ArrayList<Enemy>();
        itemsInRoom = new ArrayList<Item>();
        
        hasImage = false; 
    }
    
    /**
     * * Create a room described "description". Initially, it has no exits.
     * "description" is something like "in a kitchen" or "in an open court 
     * yard".
     * @param description The description of this room.
     * @param imageName The imageFilePath of this room.
     */
    public Room(String description, String imageName) // constructor for rooms with images
    {
        this.description = description;
        exits = new HashMap<String,Room>();
        enemiesInRoom = new ArrayList<Enemy>();
        itemsInRoom = new ArrayList<Item>();
        
        this.imageName = imageName; 
        hasImage = true; 
    }
    
    /**
     * 
     * @return Contained enemies description.
     */
    public String showEnemiesInRoom(){
        if(enemiesInRoom.isEmpty()){
            return "This Room is Empty";
        }
        return "";
    }
    
    /**
     * 
     * @return Contained items description.
     */
    public String showItemsInRoom(){
        if(itemsInRoom.isEmpty()){
            return "No items in here";
        }
        return "";
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
        return "You are " + description + ".\n" + getExitString();
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
     * 
     * @return The items contained in this room.
     */
    public List<Item> getItems()
    {
        return itemsInRoom; 
    }
    
    /**
     * 
     * @return The item picked up if itemIndex is valid, else null.
     */
    public Item pickItem(int itemIndex)
    {
        if (itemIndex < 0 || itemIndex >= itemsInRoom.size()) {
        	return null;
        }
        return itemsInRoom.remove(itemIndex);
    }
    
    /**
     * 
     * @return Whether this room has image.
     */
    public boolean hasImage() // used by gameEnginge
    {
        return this.hasImage; 
    }
    
    /**
     * 
     * @return The imageFilePath of this room.
     */
    public String getImage() // used by gameEnginge
    {
        return imageName; 
    }
}

