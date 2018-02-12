package server; 

/**
 * Exit-game command.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Exit extends Command {
	/**
	 * Constructor for this class.
	 */
	public Exit() {
	}
	
	/**
	 * Executes this command.
	 * @param p The player object which this function affects.
	 */
	public void execute(Player p) {
		//TODO: Disconnect calling player and make his client exit with a message.
	}
}
