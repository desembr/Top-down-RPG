package server.items;

/*
 * Class Equipment - template for equipment items (swords, shields, armor) in the game.
 * @author Jan Rasmussen
 * @version 2018-02-20
 */
public abstract class Equipment extends Item {
	private static final long serialVersionUID = 1L;

	protected int defenceGain, damageGain, maxHealthIncrease;

	/**
	 * Constructor for Equipment.
	 * 
	 * @param name
	 *            The name of this equipment.
	 * @param weight
	 *            The weight of this equipment.
	 * @param The
	 *            iconFilePath of this equipment.
	 */
	protected Equipment(String name, int weight, String iconFilePath) {
		super(name, weight, iconFilePath);
	}
	
	/**
	 * Is the item usable or not.
	 * 
	 * @return Whether the item implements use (Food) or not (Equipment).
	 */
	public boolean isUsable() {
		return false;
	}

	/**
	 * Gets the damage gain of this equipment.
	 * 
	 * @return The damage gain of this equipment.
	 */
	public int getDamageGain() {
		return damageGain;
	}

	/**
	 * Gets the defence gain of this equipment.
	 * 
	 * @return The defence gain of this equipment.
	 */
	public int getDefenceGain() {
		return defenceGain;
	}
	
	/**
	 * Returns the health increase of this equipment
	 * @return the field value
	 */
	
	public int getHealthIncrease()
	{
		return maxHealthIncrease; 
	}
}
