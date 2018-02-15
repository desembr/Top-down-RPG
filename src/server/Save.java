 package server; 

/**
 * Save-player-high-score command.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Save extends Command {
	/**
	 * Constructor for this class.
	 */
	public Save() {
	}
	
	/**
	 * Executes this command, sets a return message if something to write back to client.
	 * @param p The player object which this function affects.
	 * @return Whether execution of this command changed some player state.
	 */
	public boolean execute(Player p) {
		//TODO:implement saving of player high-score to file.
		return false;
	}
}
