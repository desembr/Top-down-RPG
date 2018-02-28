package server.items;

/*
 * Class Apple - can be consumed (used) for health gain.
 * @author Christer Sonesson
 * @version 2018-02-20
 */
public class Apple extends Food {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for Apple.
	 */
	public Apple() {
		super("Apple", 5, 8, "apple.png");
	}
}
