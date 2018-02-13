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
	 * Executes this command, sets a return message if something to write back to client.
	 * @param p The player object which this function affects.
	 * @return Whether execution of this command changed some player state.
	 */
	public boolean execute(Player p) {
		returnMessage = "Thank you for playing. Good bye.";
		return true;
	}
}
