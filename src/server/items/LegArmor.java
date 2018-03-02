package server.items;

/*
 * Class LegArmor - grants defence/health to a player.
 * @author Jan Rasmussen
 * @version 2018-02-24
 */
public class LegArmor extends Equipment {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor for Shield.
	 */
	public LegArmor(int damageGain, int defenceGain, int maxHealthIncrease) {
		super("LegArmor", 5, "LegArmor.png");
		this.damageGain = damageGain;
		this.defenceGain = defenceGain;
		this.maxHealthIncrease = maxHealthIncrease; 
	}

	/**
	 * Returns the same health.
	 * 
	 * @param currentHealth
	 *            The current health of the consuming/using player.
	 * @return The same health for the consuming player to use (not usable).
	 */
	public int use(int currentHealth, int maxHealth) {
		return currentHealth;
	}
	

}