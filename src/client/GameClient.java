package client;

/**
 * Main class on the client-side, which creates the UserInterface.
 * 
 * @author Tom Bjurenlind
 * @version 2018-02-26
 */
public class GameClient {
	@SuppressWarnings("unused")
	private UserInterface gui;

	/**
	 * Constructor of this class.
	 */
	public GameClient() {
		gui = new UserInterface();
	}

	/**
	 * Entry point to client.
	 * 
	 * @param args
	 *            The command-line arguments.
	 */
	public static void main(String[] args) {
		new GameClient();
	}
}
