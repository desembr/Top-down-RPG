package server.items;

import java.io.Serializable;

/*
 * Class Item - template for items in the game.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public abstract class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	protected String name, iconFilePath;
	protected int weight;

	/**
	 * Constructor for Item.
	 * 
	 * @param name
	 *            The name of this Item.
	 * @param weight
	 *            The weight of this Item.
	 * @param iconFilePath
	 *            The iconFilePath of this Item.
	 */
	protected Item(String name, int weight, String iconFilePath) {
		this.name = name;
		this.weight = weight;
		this.iconFilePath = iconFilePath;
	}

	/**
	 * 
	 * @return The name of this Item.
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return The weight of this Item.
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * 
	 * @return The iconFilePath of this Item.
	 */
	public String getIconFilePath() {
		return iconFilePath;
	}

	/**
	 * Is the item usable or not.
	 * 
	 * @return Whether the item implements use (Food) or not (Equipment).
	 */
	public abstract boolean isUsable();

	/**
	 * Returns an increased health.
	 * 
	 * @param currentHealth
	 *            The current health of the consuming/using player.
	 * @return The new increased health for the consuming player to use.
	 */
	public abstract int use(int currentHealth);
}
