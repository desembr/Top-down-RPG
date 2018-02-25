package server.entities;

/*
 * Class Goblin - represents a particular enemy in the game.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 2018-02-28
 */
public class Goblin extends Enemy {
	private static final long serialVersionUID = 1L;

	private static int num = 0;

	/**
	 * Constructor for Goblin.
	 */
	public Goblin() {
		super("Goblin" + (++num), 7, 8, 20, "res/monsters/goblin.png");
	}
}