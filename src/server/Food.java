package server;

import java.io.Serializable;

/*
 * Class Food - template for food items in the game.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public abstract class Food extends Item implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected int healthGain;
	
	/**
	 * Constructor for Food.
	 * @param name The name of this Food.
	 * @param weight The weight of this Food.
	 * @param healthGain The healthGain on use of this Food.
	 * @param iconFilePath The iconFilePath of this Food.
	 */
	protected Food(String name, int weight, int healthGain, String iconFilePath) {
		super(name, weight, iconFilePath);
		this.healthGain = healthGain;
	}
	
	/**
	 * Returns an increased health.
	 * @param currentHealth The current health of the consuming/using player.
	 * @return The new increased health for the consuming player to use.
	 */
	public int use(int currentHealth) {
		return currentHealth + healthGain;
	}
	
	/**
	 * Is the item usable or not.
	 * @return Whether the item implements use (Food) or not (Equipment).
	 */
	public boolean isUsable() {
		return true;
	}
}
