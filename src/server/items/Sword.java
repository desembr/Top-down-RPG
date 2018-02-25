package server.items;

/*
 * Class Sword - grants damage to a player.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Sword extends Equipment {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for Sword.
	 */
	public Sword(int damageGain, int defenceGain) {
		super("Sword", 25, "sword.png");
		this.damageGain = damageGain;
		this.defenceGain = defenceGain;
	}

	/**
	 * Returns the same health.
	 * 
	 * @param currentHealth
	 *            The current health of the consuming/using player.
	 * @return The same health for the consuming player to use (not usable).
	 */
	public int use(int currentHealth) {
		return currentHealth;
	}
}
