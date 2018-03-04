package server.entities;

/*
 * Class Ogre - represents a particular enemy in the game.
 * @author Christer Sonesson
 * @version 2018-02-28
 */
public class Ogre extends Enemy {
	private static final long serialVersionUID = 1L;

	private static int num = 0;

	/**
	 * Constructor for Ogre.
	 */
	public Ogre(String isLowRes) {
		super("Ogre" + (++num), 20, 25, 70, "res/monsters/"+isLowRes+"ogre.png");
	}
}