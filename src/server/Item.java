package server;

import java.io.Serializable;

/*
 * Class Item - template for items in the game.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public abstract class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name, iconFilePath;
	private int weight;
	
	protected Item(String name, int weight, String iconFilePath) {
		this.name = name;
		this.weight = weight;
		this.iconFilePath = iconFilePath;
	}
	
	public String getName() {
		return name;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public String getIconFilePath() {
		return iconFilePath;
	}
	
	/**
	 * Is the item usable or not.
	 * @return Whether the item implements use (Food) or not (Equipment).
	 */
	public abstract boolean isUsable();
}
