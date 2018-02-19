 package server; 

/**
 * Go-back-to-previous-room command.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Back extends Command {
	/**
	 * Constructor for this class.
	 */
	public Back() {
	}
	
	/**
	 * Executes this command, sets a return message if something to write back to client.
	 * @param p The player object which this function affects.
	 * @return Whether execution of this command changed some player state.
	 */
	public boolean execute(Player p) {
		p.setCmdReturnMsg(null);
		if (p.goBackARoom()) {
			return true;
		}
		return false;
	}
}
