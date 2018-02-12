package server;

/*
 * Class Pie - can be consumed (used) for health gain.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Pie extends Food {
	private static final long serialVersionUID = 1L;
	
	private static int weight = 10, healthGain = 12;
	private static String name = "Pie", iconFilePath = "pie.png";
	
	public Pie() {
		super(name, weight, healthGain, iconFilePath);
	}
}
