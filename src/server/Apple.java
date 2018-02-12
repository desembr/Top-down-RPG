package server;

/*
 * Class Apple - can be consumed (used) for health gain.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Apple extends Food {
	private static final long serialVersionUID = 1L;
	
	private static int weight = 5, healthGain = 8;
	private static String name = "Apple", iconFilePath = "apple.png";
	
	public Apple() {
		super(name, weight, healthGain, iconFilePath);
	}
}
