package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * Class Player - contains state for the player (current room, items etc).
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Player extends Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static int num = 0, maxWeight = 40;
	
	private List<Item> items;
	private List<Room> previousRooms;
	private Room currentRoom;
	
	private int score, weight; 
	
	private String cmdReturnMsg;
	
	/**
	 * Constructor for Player
	 * @param startRoom The starting room.
	 */
    public Player(Room startRoom)
    {
    	super("Player" + (++num), 15, 32, 100, "res/player/player1_no_armor.png");
    	this.currentRoom = startRoom;
    	items = new ArrayList<>();
    	previousRooms = new ArrayList<>();
    	score = weight = 0;
    	
    	currentRoom.addPlayer(this);
    }
    
    /**
     * Returns this player's inventory (items).
     * @return The items in this player's inventory.
     */
    public List<Item> getItems() {
    	return items;
    }
    
    /**
     * Attempts to consume a food in inventory.
     * @param itemName The name of the food to consume.
     * @return Whether the item could be consumed or not.
     */
    public boolean useItem(String itemName) {
    	for (int i = 0; i < items.size(); i++) {
        	Item item = items.get(i);
        	if (item instanceof Food) {
	        	if (item.getName().toLowerCase().equals(itemName.toLowerCase())) {
	        		this.health = item.use(health);
	        		weight -= item.getWeight();
	        		items.remove(i);
	        		return true;
	        	}
        	}
    	}
        return false;
    }
    
    /**
     * Attempts to drop an item in inventory of this player.
     * @param itemName The name of the item to drop.
     * @return Whether the player could drop the specified item or not (invalid index).
     */
    public boolean dropItem(String itemName) {
    	for (int i = 0; i < items.size(); i++) {
        	Item item = items.get(i);
        	if (item.getName().toLowerCase().equals(itemName.toLowerCase())) {
        		weight -= item.getWeight();
        		currentRoom.addItem(item);
        		items.remove(i);
        		return true;
        	}
        }
        return false;
    }
    
    /**
     * Attempts to pick up an item and add to this player's inventory.
     * @param item The item to pick up.
     * @return Whether the player had enough room left in his inventory (depends on weight).
     */
    public boolean pickItem(Item item) {
    	if (weight + item.getWeight() > maxWeight) {
    		return false;
    	}
    	items.add(item);
    	return true;
    }
    
    /**
     * Attempts to go to a neighboring room of this player's current room.
     * @param direction The direction to attempt to exit through.
     */
    public boolean goRoom(String direction) {
    	Room r = currentRoom.getExit(direction);
    	if (r == null) {
    		return false;
    	}
    	else {
    		currentRoom.flushGraphics(); 
    		previousRooms.add(currentRoom);
    		currentRoom.removePlayer(this);
    		currentRoom = r; 
    		currentRoom.addPlayer(this);
    		return true;
    	}
    }
    
    /**
     * Attempts to go back to previous room.
     * @return Whether the player could go back further to previous rooms or not.
     */
    public boolean goBackARoom() {
    	if (previousRooms.size() > 0) {
    		currentRoom.flushGraphics();
    		Room r = previousRooms.remove(previousRooms.size() - 1);
    		currentRoom = r;
    		return true;
    	}
    	return false;
    }
    
    /**
     * Logic for attacking an entity, grants score points if the enemy dies.
     * @param e The entity to attack.
     */
    public void attack(Entity e) {
    	if (e != null) {
    		e.lowerHealth(damage);
    		if (e.getIsDead())
    			score += 5;
    	}
    }
    
    /**
     * Gets string for this player's inventory.
     * @return A descriptive string for display of this player's inventory.
     */
    public String showInventory()
    {
    	String ret = "";
    	ret += "Items in inventory: ";
    	if (items.size() > 0) {
    		for (Item i : items) {
        		ret += i.getName() + " ";
        	}
    	}
    	else
    		ret += "None\n";

    	ret += "Score: " + score;
    	
    	return ret;
    }

    /**
     * Loads in a saved player state.
     * @param p The player to load.
     * @return Whether load succeeded or not.
     */
	public boolean loadPlayer(Player p) {
		// Tog bort laddning av rum då det sparade rummets information är ouppdaterad
        this.health = p.health;
        this.items = p.items;
        this.score = p.score;
        this.items = p.items;
        this.damage = p.damage;
        this.defence = p.defence;
        this.weight = p.weight;
        
        return true;
    }
    
    /**
     * Returns health of this player.
     * @return The current score of this player.
     */
	public int getScore() {
    	return score;
    }
	
	/**
     * Returns weight of this player.
     * @return The weight.
     */
	public int getWeight() {
    	return weight;
    }
	
	/**
     * Returns the current room.
     * @return The current room of this player.
     */
	public Room getRoom() {
    	return currentRoom;
    }
	
	/**
     * Returns a message from the previous command, if there was none
     * null is returned to signal this.
     * @return The cmdReturnMsg
     */
	public String getCmdReturnMsg() {
    	return cmdReturnMsg;
    }
	
	/**
     * Sets a message from the previous command, for corresponding Client to display.
     * @param msg The message to set.
     */
	public void setCmdReturnMsg(String msg) {
    	cmdReturnMsg = msg;
    }
}
