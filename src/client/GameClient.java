package client; 

/**
 * Main class on the client-side, which creates core objects and stores state.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class GameClient {
	private UserInterface gui;
	
	public GameClient() {
		gui = new UserInterface();
	}
	
	public static void main(String[] args) {
		new GameClient();
	}
}
