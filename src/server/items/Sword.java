package server.items;

/*
 * Class Sword - grants damage to a player.
 * @author Emir Zivcic
 * @version 2018-02-22
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
}
