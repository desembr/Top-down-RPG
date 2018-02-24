package server; 

/**
 * Go-to-another-room command.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Go extends Command {
	/**
	 * Constructor for this class.
	 */
	public Go() {
	}
	
	/**
	 * Executes this command, sets a return message if something to write back to client.
	 * @param p The player object which this function affects.
	 * @return Whether execution of this command changed some player state.
	 */
	public boolean execute(Player p) {
		p.setCmdReturnMsg(this.getClass().getName());
		if (secondWord != null) {
			boolean stateChange = p.goRoom(secondWord);
			if (stateChange) {
				return true;
			}
		}
		return false;
	}
}
