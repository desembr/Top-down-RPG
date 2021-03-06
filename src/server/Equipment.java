package server;

/*
 * Class Equipment - template for equipment items (swords, shields, armor) in the game.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public abstract class Equipment extends Item {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for Equipment.
	 * @param name The name of this equipment.
	 * @param weight The weight of this equipment.
	 * @param The iconFilePath of this equipment.
	 */
	protected Equipment(String name, int weight, String iconFilePath) {
		super(name, weight, iconFilePath);
	}

	/**
	 * Is the item usable or not.
	 * @return Whether the item implements use (Food) or not (Equipment).
	 */
	public boolean isUsable() {
		return false;
	}
}
