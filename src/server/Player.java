package server;

import java.util.ArrayList;
import java.util.List;

/*
 * Class Player - contains state for the player (current room, items etc).
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Player extends Entity {
	private static final long serialVersionUID = 1L;
	
	private static int num = 0, maxWeight = 40;
	
	private List<Item> items;
	private List<Room> previousRooms;
	private Room currentRoom;
	private int score, highScore, weight; 
    
	/**
	 * Constructor for Player
	 */
    public Player(Room startRoom)
    {
    	super("Player" + (++num), 30, 32, 100, "player_no_armor_64x64.png");
    	this.currentRoom = startRoom;
    	items = new ArrayList<>();
    	score = highScore = weight = 0;
        //spritePath = "player_no_armor_64x64.png"; 
    }
    
    /**
     * 
     * @return The items in this player's inventory.
     */
    public List<Item> getItems() {
    	return items;
    }
    
    /**
     * Attempts to save a highscore for this player on this server.
     */
    public void saveHighScore() {
    	//TODO: implement.
    }
    
    /**
     * Attempts to consume a food in inventory.
     * @param itemIndex The index of the food to use/consume.
     * @return Whether the item could be consumed or not.
     */
    public boolean useItem(int itemIndex) {
    	if (itemIndex < 0 || itemIndex >= items.size()) {
    		return false;
    	}
    	Item item = items.get(itemIndex);
    	if (item.isUsable()) {
    		this.health = item.use(health);
    		weight -= items.get(itemIndex).getWeight();
    		items.remove(itemIndex);
    		return true;
    	}
    	return false;
    }
    
    /**
     * Attempts to drop an item in inventory of this player.
     * @param itemIndex The index of the item to drop.
     * @return Whether the player could drop the specified item or not (invalid index).
     */
    public boolean dropItem(int itemIndex) {
    	if (itemIndex < 0 || itemIndex >= items.size()) {
    		return false;
    	}
    	weight -= items.get(itemIndex).getWeight();
		items.remove(itemIndex);
		return true;
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
    		previousRooms.add(currentRoom);
    		currentRoom = r;
    		return true;
    	}
    }
    
    /**
     * Attempts to go back to previous room.
     * @return Whether the player could go back further to previous rooms or not.
     */
    public boolean goBackARoom() {
    	if (previousRooms.size() > 0) {
    		Room r = previousRooms.remove(previousRooms.size() - 1);
    		currentRoom = r;
    		return true;
    	}
    	return false;
    }
    
    /**
     * Returns health of this player.
     * @return The current score of this player.
     */
	public int getScore() {
    	return score;
    }
	
	/**
     * Returns highscore of this player.
     * @return The highscore of this player.
     */
	public int getHighScore() {
    	return highScore;
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
      
    /*public String getGraphic()
    {
        return spritePath; 
    }
    
    public void setGraphic(String spritePath)
    {
        this.spritePath = spritePath; 
    }*/
}
