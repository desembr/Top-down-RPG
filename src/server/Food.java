package server;

import java.io.Serializable;

/*
 * Class Food - template for food items in the game.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public abstract class Food extends Item implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int healthGain;

	protected Food(String name, int weight, int healthGain, String iconFilePath) {
		super(name, weight, iconFilePath);
		this.healthGain = healthGain;
	}
	
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
