package server.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import server.GameEngine;
import server.Room;
import server.items.Equipment;
import server.items.Food;
import server.items.Item;

/*
 * Class Player - contains state for the player (current room, items etc).
 * @author Emir Zivcic
 * @version 2018-02-28
 */
public class Player extends Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	private static int num = 0, maxWeight = 100;

	private List<Item> items;
	private List<Room> previousRooms;
	private Room currentRoom;

	private int score, weight;

	private String cmdReturnMsg;

	/**
	 * Constructor for Player
	 * 
	 * @param startRoom
	 *            The starting room.
	 */
	public Player(Room startRoom) {
		super("Player" + (++num), 15, 32, 100, "res/player/player1_no_armor.png");
		this.currentRoom = startRoom;
		items = new ArrayList<>();
		previousRooms = new ArrayList<>();
		score = weight = 0;

		currentRoom.addPlayer(this);
	}

	/**
	 * Returns this player's inventory (items).
	 * 
	 * @return The items in this player's inventory.
	 */
	public List<Item> getItems() {
		return items;
	}

	/**
	 * Attempts to consume a food in inventory.
	 * 
	 * @param itemName
	 *            The name of the food to consume.
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
	 * 
	 * @param itemName
	 *            The name of the item to drop.
	 * @return Whether the player could drop the specified item or not (invalid
	 *         index).
	 */
	public boolean dropItem(String itemName) {
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			if (item.getName().toLowerCase().equals(itemName.toLowerCase())) {
				// Remove equipment gains.
				if (item instanceof Equipment) {
					Equipment eq = (Equipment) item;
					damage -= eq.getDamageGain();
					defence -= eq.getDefenceGain();
				}

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
	 * 
	 * @param item
	 *            The item to pick up.
	 * @return Whether the player had enough room left in his inventory (depends
	 *         on weight).
	 */
	public boolean pickItem(Item item) {
		if (weight + item.getWeight() > maxWeight) {
			// Add the item back to the room if weight is too high.
			currentRoom.addItem(item);
			return false;
		}
		
		// Make so that you can only carry one sword and shield
		// Use the new weapon if it is better, otherwise discard it
		
		for (int i = 0; i < items.size(); i++ )
		{
			Item checkItem = items.get(i); 
			if (checkItem.getName().equals("Sword") && item.getName().equals("Sword") )
			{
				Equipment eqOld = (Equipment) checkItem;
				Equipment eqNew = (Equipment) item;
				if (eqNew.getDamageGain() > eqOld.getDamageGain() ) // if the new sword is better
				{
					dropItem(checkItem.getName() ); // drop the sword you are holding
					
					int damageDifference = eqNew.getDamageGain()-eqOld.getDamageGain(); 
					setCmdReturnMsg("\nThis new sword does " + damageDifference + " points of damage more than the old!\n Attack-rating increased\n"); 
					
					break; // stop looping
				}
				else if (eqNew.getDamageGain() <= eqOld.getDamageGain() ) // if the new sword is worse or equal
				{
					setCmdReturnMsg("\nThis new sword is worse than your current one, no use in taking it\n"); 
					return true; // do not pick up
				}
			}
			else if (checkItem.getName().equals("Shield") && item.getName().equals("Shield") )
			{
				Equipment eqOld = (Equipment) checkItem;
				Equipment eqNew = (Equipment) item;
				if (eqNew.getDefenceGain() > eqOld.getDefenceGain() ) // if the new shield is better
				{
					dropItem(checkItem.getName() ); //drop the shield you are holding
					
					int defenceDifference = eqNew.getDefenceGain()-eqOld.getDefenceGain(); 
					setCmdReturnMsg("\nThis new shield has " + defenceDifference + " more points of armor than the old one!\n Defence increased\n"); 
					
					break; // stop looping
				}
				else if (eqNew.getDefenceGain() <= eqOld.getDefenceGain() ) // if the new shield is worse or equal
				{
					setCmdReturnMsg("\nThis new shield is worse than your current one, no use in taking it\n"); 
					return true; // do not pick up
				}
			}
			// if you don't have a sword or shield, or the item is something else, continue
			
		}
		
		// Add equipment gains.
		if (item instanceof Equipment) {
			Equipment eq = (Equipment) item;
			damage += eq.getDamageGain();
			defence += eq.getDefenceGain();
		}
        
		weight += item.getWeight();
		items.add(item);
		return true;
	}

	/**
	 * Attempts to go to a neighboring room of this player's current room.
	 * 
	 * @param direction
	 *            The direction to attempt to exit through.
	 * @return Whether player successfully switched room.
	 */
	public boolean goRoom(String direction) {
		Room r = currentRoom.getExit(direction);
		if (r == null) {
			return false;
		} else {
			previousRooms.add(currentRoom);
			currentRoom.removePlayer(this);
			currentRoom = r;
			currentRoom.addPlayer(this);
			return true;
		}
	}

	/**
	 * Attempts to go back to previous room.
	 * 
	 * @return Whether the player could go back further to previous rooms or
	 *         not.
	 */
	public boolean goBackARoom() {
		if (previousRooms.size() > 0) {
			Room r = previousRooms.remove(previousRooms.size() - 1);
			currentRoom.removePlayer(this);
			currentRoom = r;
			currentRoom.addPlayer(this);
			return true;
		}
		return false;
	}

	/**
	 * Logic for attacking an entity, grants score points if the enemy dies.
	 * Entity e will attempt to strike back, defence affects hit-chance.
	 * 
	 * @param e
	 *            The entity to attack.
	 * @return Whether attack landed, false if entity is already dead.
	 */
	public boolean attack(Entity e) {
		if (e != null) {
			if (e.getIsDead()) {
				return false;
			}
			e.lowerHealth(damage);
			
			setCmdReturnMsg("\nYou strike true and hit the enemy for " + damage + " damage\n"); 
			
			System.out.println(); 
			
			if (e.getIsDead())
				score += 5;

			// Enemy e attempts to strike back at the attacking player.
			if (rand.nextInt(defence) <= 16)
			{
				setCmdReturnMsg("\nYou strike true and hit the enemy for " + damage + " damage\n"
						+ "unfortunately the enemy does the same to you for " + e.getDamage() +" damage\n"); 
				
				lowerHealth(e.getDamage());
			}
			
		}
		return true;
	}
	
	
	/**
	 * Gets string for this player's inventory.
	 * 
	 * @return A descriptive string for display of this player's inventory.
	 */
	public String showInventory() {
		String ret = "";
		ret += "Items in inventory: ";
		if (items.size() > 0) {
			for (Item i : items) {
				ret += i.getName() + " ";
			}
		} else
			ret += "None";

		ret += "\nScore: " + score;

		return ret;
	}

	/**
	 * Loads in a saved player state.
	 * 
	 * @param p
	 *            The player to load.
	 * @return Whether load succeeded or not.
	 */
	public boolean loadPlayer(Player p) {
		this.health = p.health;
		this.items = p.items;
		this.score = p.score;
		this.items = p.items;
		this.damage = p.damage;
		this.defence = p.defence;
		this.weight = p.weight;

		Room r = GameEngine.getRoom(p.getRoom().getShortDescription());
		if (r != null) {
			this.currentRoom = r;
			r.addPlayer(this);
		}

		return true;
	}

	/**
	 * Returns health of this player.
	 * 
	 * @return The current score of this player.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Returns weight of this player.
	 * 
	 * @return The weight.
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Returns the current room.
	 * 
	 * @return The current room of this player.
	 */
	public Room getRoom() {
		return currentRoom;
	}

	/**
	 * Returns the previous room.
	 * 
	 * @return The previous room of this player.
	 */
	public Room getPreviousRoom() {
		if (previousRooms.size() > 0)
			return previousRooms.get(previousRooms.size() - 1);

		return null;
	}

	/**
	 * Returns a message from the previous command, if there was none null is
	 * returned to signal this.
	 * 
	 * @return The cmdReturnMsg
	 */
	public String getCmdReturnMsg() {
		return cmdReturnMsg;
	}

	/**
	 * Sets a message from the previous command, for corresponding Client to
	 * display.
	 * 
	 * @param msg
	 *            The message to set.
	 */
	public void setCmdReturnMsg(String msg) {
		cmdReturnMsg = msg;
	}

	/**
	 * Returns the max weight carried for a player.
	 * 
	 * @return The max weight.
	 */
	public int getMaxWeight() {
		return maxWeight;
	}
}
