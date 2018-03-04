package server.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import server.GameEngine;
import server.Room;
import server.items.ArmArmor;
import server.items.ChestArmor;
import server.items.Equipment;
import server.items.Food;
import server.items.Helmet;
import server.items.Item;
import server.items.LegArmor;

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

	private String cmdReturnMsg, printReturnMsg;

	private boolean wearsHelmet;
	private boolean wearsChestArmor;
	private boolean wearsLegArmor;
	private boolean wearsArmArmor;

	private int maxHealth;
	
	private boolean isLowRes; 

	/**
	 * Constructor for Player
	 * 
	 * @param startRoom
	 *            The starting room.
	 */
	public Player(Room startRoom) {
		
		
		super("Player" + (++num), 15, 32, 100, "res/player/"+startRoom.getResolutionString()+"player1_no_armor.png");
		
		isLowRes = startRoom.getResolution(); 
		this.currentRoom = startRoom;
		
		items = new ArrayList<>();
		previousRooms = new ArrayList<>();
		score = weight = 0;

		wearsHelmet = false;
		wearsChestArmor = false;
		wearsLegArmor = false;
		wearsArmArmor = false;

		maxHealth = 100;

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
					this.health = item.use(health, maxHealth);
					weight -= item.getWeight();
					items.remove(i);
					setPrintReturnMsg("Your attack is now " + damage + ", your defence is " + defence + " and your weight is " + weight
							+ "/" + maxWeight + "\n" + showInventory());
					return true;
				}
			}
		}
		setPrintReturnMsg("That item doesn't exist!");
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

					// Update armor graphics on player
					if (item instanceof ArmArmor)
						wearsArmArmor = false;
					else if (item instanceof ChestArmor)
						wearsChestArmor = false;
					else if (item instanceof LegArmor)
						wearsLegArmor = false;
					else if (item instanceof Helmet)
						wearsHelmet = false;

					updatePlayerGraphic();
				}
				// remove item from inventory, add it to room, decrease
				// player-weight by item-weight.
				weight -= item.getWeight();
				currentRoom.addItem(item);
				items.remove(i);
				setPrintReturnMsg("Your attack is now " + damage + ", your defence is " + defence + ", your weight is " + weight + "/"
						+ maxWeight + " and your max health is " + maxHealth + "\n" + showInventory());
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
			setCmdReturnMsg("Your inventory is currently full, try dropping something!");
			return false;
		}

		// Make so that you can only carry one sword and shield
		// Use the new weapon if it is better, otherwise discard it

		for (int i = 0; i < items.size(); i++) {
			Item checkItem = items.get(i);
			if (checkItem.getName().equals("Sword") && item.getName().equals("Sword")) {
				Equipment eqOld = (Equipment) checkItem;
				Equipment eqNew = (Equipment) item;
				if (eqNew.getDamageGain() > eqOld.getDamageGain()) // if the new
																	// sword is
																	// better
				{
					dropItem(checkItem.getName()); // drop the sword you are
													// holding

					int damageDifference = eqNew.getDamageGain() - eqOld.getDamageGain();
					setPrintReturnMsg(
							"This new sword does " + damageDifference + " points of damage more than the old!\n Attack-rating increased");

					break; // stop looping
				} else if (eqNew.getDamageGain() <= eqOld.getDamageGain()) // if
																			// the
																			// new
																			// sword
																			// is
																			// worse
																			// or
																			// equal
				{
					setPrintReturnMsg("This new sword is worse than your current one, no use in taking it");
					return false; // do not pick up
				}
			} else if (checkItem.getName().equals("Shield") && item.getName().equals("Shield")) {
				Equipment eqOld = (Equipment) checkItem;
				Equipment eqNew = (Equipment) item;
				if (eqNew.getDefenceGain() > eqOld.getDefenceGain()) // if the
																		// new
																		// shield
																		// is
																		// better
				{
					dropItem(checkItem.getName()); // drop the shield you are
													// holding

					int defenceDifference = eqNew.getDefenceGain() - eqOld.getDefenceGain();
					setPrintReturnMsg(
							"This new shield has " + defenceDifference + " more points of armor than the old one! Defence increased");

					break; // stop looping
				} else if (eqNew.getDefenceGain() <= eqOld.getDefenceGain()) // if
																				// the
																				// new
																				// shield
																				// is
																				// worse
																				// or
																				// equal
				{
					setPrintReturnMsg("This new shield is worse than your current one, no use in taking it");
					return false; // do not pick up
				}
			}
			// if you don't have a sword or shield, or the item is something
			// else, continue

		}

		// Add equipment gains.
		if (item instanceof Equipment) {
			Equipment eq = (Equipment) item;

			if (eq.getHealthIncrease() > 0) // equip a piece of armor if the
											// current item is one, armor is not
											// droppable
			{
				if (eq.getName().equals("Helmet")) {
					if (this.wearsHelmet == false) {
						this.wearsHelmet = true;
					} else {
						setPrintReturnMsg("You are already wearing a helmet");
						return false;
					}
				} else if (eq.getName().equals("ChestArmor")) {
					if (this.wearsChestArmor == false) {
						this.wearsChestArmor = true;
					} else {
						setPrintReturnMsg("You are already wearing chest armor");
						return false;
					}
				} else if (eq.getName().equals("ArmArmor")) {
					if (this.wearsArmArmor == false) {
						this.wearsArmArmor = true;
					} else {
						setPrintReturnMsg("You are already wearing arm armor");
						return false;
					}
				} else if (eq.getName().equals("LegArmor")) {
					if (this.wearsLegArmor == false) {
						this.wearsLegArmor = true;
					} else {
						setPrintReturnMsg("You are already wearing leg armor");
						return false;
					}
				}
				this.health += eq.getHealthIncrease();
				this.maxHealth += eq.getHealthIncrease();
			}

			updatePlayerGraphic(); // updates the player sprite in case there
									// were any changes in armor

			damage += eq.getDamageGain();
			defence += eq.getDefenceGain();
		}

		weight += item.getWeight();
		items.add(item);

		if (printReturnMsg == null) {
			setPrintReturnMsg("Your attack is now " + damage + ", your defence is " + defence + ", your weight is " + weight + "/"
					+ maxWeight + " and your max health is " + maxHealth + "\n" + showInventory());
		}

		return true;
	}

	/**
	 * Sets the player sprite to the correct one based on which pieces of armor
	 * he is wearing
	 */
	private void updatePlayerGraphic() {
		if (wearsHelmet == false && wearsChestArmor == false && wearsArmArmor == true && wearsLegArmor == false) // only
																													// arm
		{
			setIconFilePath("res/player/"+currentRoom.getResolutionString()+"player1_arms.png");
		} else if (wearsHelmet == false && wearsChestArmor == false && wearsArmArmor == true && wearsLegArmor == true) // leg
																														// arm
		{
			setIconFilePath("res/player/"+currentRoom.getResolutionString()+"player1_arms_legs.png");
		} else if (wearsHelmet == false && wearsChestArmor == true && wearsArmArmor == false && wearsLegArmor == false) // chest
																														// only
		{
			setIconFilePath("res/player/"+currentRoom.getResolutionString()+"player1_chest.png");
		} else if (wearsHelmet == false && wearsChestArmor == true && wearsArmArmor == true && wearsLegArmor == false) // chest
																														// arm
		{
			setIconFilePath("res/player/"+currentRoom.getResolutionString()+"player1_chest_arm.png");
		} else if (wearsHelmet == false && wearsChestArmor == true && wearsArmArmor == true && wearsLegArmor == true) // chest
																														// leg
		{
			setIconFilePath("res/player/"+currentRoom.getResolutionString()+"player1_chest_arms_legs.png");
		} else if (wearsHelmet == true && wearsChestArmor == true && wearsArmArmor == false && wearsLegArmor == true) // chest
																														// +
		{
			setIconFilePath("res/player/"+currentRoom.getResolutionString()+"player1_chest_helm_leg.png");
		} else if (wearsHelmet == false && wearsChestArmor == true && wearsArmArmor == false && wearsLegArmor == true) // chest
																														// leg
		{
			setIconFilePath("res/player/"+currentRoom.getResolutionString()+"player1_chest_legs.png");
		} else if (wearsHelmet == true && wearsChestArmor == true && wearsArmArmor == true && wearsLegArmor == true) // full
																														// suit
		{
			setIconFilePath("res/player/"+currentRoom.getResolutionString()+"player1_full_armor.png");
		} else if (wearsHelmet == true && wearsChestArmor == false && wearsArmArmor == false && wearsLegArmor == false) // only
																														// helmet
		{
			setIconFilePath("res/player/"+currentRoom.getResolutionString()+"player1_helm.png");
		} else if (wearsHelmet == true && wearsChestArmor == false && wearsArmArmor == true && wearsLegArmor == false) // helmet
																														// +
		{
			setIconFilePath("res/player/"+currentRoom.getResolutionString()+"player1_helm_arms.png");
		} else if (wearsHelmet == true && wearsChestArmor == false && wearsArmArmor == true && wearsLegArmor == true) // helmet
																														// +
		{
			setIconFilePath("res/player/"+currentRoom.getResolutionString()+"player1_helm_arms_legs.png");
		} else if (wearsHelmet == true && wearsChestArmor == true && wearsArmArmor == false && wearsLegArmor == false) // helmet
																														// chest
		{
			setIconFilePath("res/player/"+currentRoom.getResolutionString()+"player1_helm_chest.png");
		} else if (wearsHelmet == true && wearsChestArmor == true && wearsArmArmor == true && wearsLegArmor == false) // helmet
																														// arms
		{
			setIconFilePath("res/player/"+currentRoom.getResolutionString()+"player1_helm_chest_arms.png");
		} else if (wearsHelmet == true && wearsChestArmor == false && wearsArmArmor == false && wearsLegArmor == true) // helmet
																														// legs
		{
			setIconFilePath("res/player/"+currentRoom.getResolutionString()+"player1_helm_legs.png");
		} else if (wearsHelmet == false && wearsChestArmor == false && wearsArmArmor == false && wearsLegArmor == true) // legs
		{
			setIconFilePath("res/player/"+currentRoom.getResolutionString()+"player1_legs.png");
		} else // Wears nothing, update in case player dropped last piece of
				// armor
		{
			setIconFilePath("res/player/"+currentRoom.getResolutionString()+"player1_no_armor.png");
		}

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
			setCmdReturnMsg("There is no room in that direction!");
			return false;
		} else {
			for (Enemy enemy : currentRoom.getEnemies()) {
				if (!enemy.isDead) {
					setCmdReturnMsg("You must clear all monsters first!");
					return false; // can not exit the room if enemies are
									// present
				}
			}
		}
		previousRooms.add(currentRoom);
		currentRoom.removePlayer(this);
		currentRoom = r;
		currentRoom.addPlayer(this);
		setPrintReturnMsg(currentRoom.getLongDescription());
		return true;

	}

	/**
	 * Attempts to go back to previous room.
	 * 
	 * @return Whether the player could go back further to previous rooms or
	 *         not.
	 */
	public boolean goBackARoom() {
		if (previousRooms.size() > 0) {
			for (Enemy enemy : currentRoom.getEnemies()) {
				if (!enemy.isDead) {
					return false; // can not exit the room if enemies are
									// present
				}
			}
			Room r = previousRooms.remove(previousRooms.size() - 1);
			currentRoom.removePlayer(this);
			currentRoom = r;
			currentRoom.addPlayer(this);
			setPrintReturnMsg(currentRoom.getLongDescription());
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

			setPrintReturnMsg("You strike true and hit the enemy for " + damage + " damage");

			// Enemy e attempts to strike back at the attacking player.
			if (rand.nextInt(defence) <= 16) {
				printReturnMsg += "\nunfortunately the enemy does the same to you for " + e.getDamage() + " damage.\n"
						+ "Your health is now " + health;

				lowerHealth(e.getDamage());
			}
			// If enemy died, grant score to player.
			if (e.getIsDead()) {
				score += 5;
				printReturnMsg += "\nYou killed an enemy! Your score is now " + score + " points";
			}

		}
		checkVictory(e); // check if the player won by killing the final boss or
							// not
		return true;
	}

	/**
	 * Checks to see if the player defeated the final boss or not, the victory
	 * condition of the game
	 * 
	 * @param e
	 *            The entity to check.
	 */
	public void checkVictory(Entity e) {
		if (e instanceof Boss && e.getIsDead()) {

			setPrintReturnMsg("\nCONGRATULATIONS!!! You beat the game!\n" + "Your final score was: " + this.score + " points.");

		}
	}

	/**
	 * Gets string for this player's inventory.
	 * 
	 * @return A descriptive string for display of this player's inventory.
	 */
	public String showInventory() {
		String ret = "";
		ret += "Items currently in your inventory: ";
		if (items.size() > 0) {
			for (Item i : items) {
				ret += i.getName() + " ";
			}
		} else
			ret += "None";

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
		this.wearsArmArmor = p.wearsArmArmor;
		this.wearsChestArmor = p.wearsChestArmor;
		this.wearsHelmet = p.wearsHelmet;
		this.wearsLegArmor = p.wearsLegArmor;

		// Remove this player from current room.
		getRoom().removePlayer(this);

		// Update player graphics with equipped armor items.
		updatePlayerGraphic();

		// Get updated version of saved room.
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
	 * Returns a message from the previous command to be printed.
	 * 
	 * @return The message to be printed on Client.
	 */
	public String getPrintReturnMsg() {
		return printReturnMsg;
	}

	/**
	 * Sets a message from the previous command, to be printed.
	 * 
	 * @param msg
	 *            The message to set.
	 */
	public void setPrintReturnMsg(String msg) {
		printReturnMsg = msg;
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
