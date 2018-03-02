package client;

/**
 * Main class on the client-side program, which creates and kicks off core
 * objects.
 * 
 * @author Tom Bjurenlind
 * @version 2018-02-26
 */
public class GameClient {
	private UserInterface gui;
	private Client client;

	/**
	 * Constructor of this class.
	 */
	public GameClient() {
		client = new Client();
		gui = new UserInterface(client);
		client.addObserver(gui);
		client.start();
	}

	/**
	 * Entry point to Client program.
	 */
	public static void main(String[] args) {
		new GameClient();
	}
}
