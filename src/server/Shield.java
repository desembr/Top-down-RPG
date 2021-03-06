package server;

/*
 * Class Shield - grants defence to a player.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Shield extends Equipment {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor for Shield.
	 */
	public Shield() {
		super("Shield", 30, "Shield.png");
	}

	/**
	 * Returns the same health.
	 * @param currentHealth The current health of the consuming/using player.
	 * @return The same health for the consuming player to use (not usable).
	 */
	public int use(int currentHealth) {
		return currentHealth;
	}
}
