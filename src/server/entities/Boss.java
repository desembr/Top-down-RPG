package server.entities;

/*
 * Class Boss - represents a particular enemy in the game.
 * @author Christer Sonesson
 * @version 2018-02-28
 */
public class Boss extends Enemy {
	private static final long serialVersionUID = 1L;

	private static int num = 0;

	/**
	 * Constructor for Boss.
	 */
	public Boss(String isLowRes) {
		
		
		
		super("Boss" + (++num), 50, 50, 200, "res/monsters/"+isLowRes+"boss_stage_1.png");
	}
}