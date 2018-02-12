package server;

/*
 * Class Sword - grants damage to a player.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Sword extends Equipment {
	private static final long serialVersionUID = 1L;
	
	private static int weight = 25;
	private static String name = "Sword", iconFilePath = "sword.png";
	
	public Sword() {
		super(name, weight, iconFilePath);
	}
}
