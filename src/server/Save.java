 package server; 

/**
 * Save-player-highscore command.
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
	 * Executes this command.
	 * @param p The player object which this function affects.
	 * @return Whether execution of this command changed some player state.
	 */
	public boolean execute(Player p) {
		return false;
	}
}
