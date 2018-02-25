package server.entities; 

/*
 * Class Orc - represents a particular enemy in the game.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 2018-02-28
 */
public class Orc extends Enemy {
	private static final long serialVersionUID = 1L;
	
	private static int num = 0;
	
	/**
	 * Constructor for Orc.
	 */
    public Orc(){
        super("Orc" + (++num), 10, 15, 40, "res/monsters/Orc.png");
    }
}