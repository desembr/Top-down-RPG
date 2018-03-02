package server.items;

/*
 * Class Shield - grants defence to a player.
 * @author Jan Rasmussen
 * @version 2018-02-24
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
}
