package server.items;

/*
 * Class Shield - grants defence to a player.
 * @author Jan Rasmussen
 * @version 1.0
 */
public class Shield extends Equipment {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for Shield.
	 */
	public Shield(int damageGain, int defenceGain) {
		super("Shield", 30, "Shield.png");
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
