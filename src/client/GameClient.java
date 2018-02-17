package client;

/**
 * Main class on the client-side, which creates core objects and stores state.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class GameClient {
	private UserInterface gui;
	Client client;
	
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
