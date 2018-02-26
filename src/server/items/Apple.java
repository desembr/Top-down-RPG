package server.items;

/*
 * Class Apple - can be consumed (used) for health gain.
 * @author Jan Rasmussen
 * @version 1.0
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
