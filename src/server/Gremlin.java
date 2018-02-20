package server; 

/*
 * Class Goblin - represents a particular enemy in the game.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Gremlin extends Enemy {
	private static final long serialVersionUID = 1L;
	
	private static int num = 0;
	
	/**
	 * Constructor for Goblin.
	 */
    public Gremlin(){
        super("Gremlin" + (++num), 3, 5, 10, "res/monsters/gremlin.png");
    }
}