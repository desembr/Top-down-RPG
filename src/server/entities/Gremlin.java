package server.entities;

/*
 * Class Goblin - represents a particular enemy in the game.
 * @author  Christer Sonesson
 * @version 2018-02-28
 */
public class Gremlin extends Enemy {
	private static final long serialVersionUID = 1L;

	private static int num = 0;

	/**
	 * Constructor for Goblin.
	 */
	public Gremlin(String isLowRes) {
		super("Gremlin" + (++num), 3, 5, 10, "res/monsters/"+isLowRes+"gremlin.png");
	}
}