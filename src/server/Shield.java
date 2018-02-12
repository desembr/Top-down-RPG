package server;

/*
 * Class Shield - grants defence to a player.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Shield extends Equipment {
	private static final long serialVersionUID = 1L;
	
	private static int weight = 30;
	private static String name = "Shield", iconFilePath = "Shield.png";
	
	public Shield() {
		super(name, weight, iconFilePath);
	}
}
