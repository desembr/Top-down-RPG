 package server; 

/**
 * Print-help-to-a-user command.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Help extends Command {
	/**
	 * Constructor for this class.
	 */
	public Help() {
	}
	
	/**
	 * Executes this command.
	 * @param p The player object which this function affects.
	 * @return Whether execution of this command changed some player state.
	 */
	public boolean execute(Player p) {
		return true;
	}
}
