 package server; 

/**
 * Pick-item command.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Pick extends Command {
	/**
	 * Constructor for this class.
	 */
	public Pick() {
		
	}
	
	/**
	 * Executes this command, sets a return message if something to write back to client.
	 * @param p The player object which this function affects.
	 * @return Whether execution of this command changed some player state.
	 */
	public boolean execute(Player p) {
		if (secondWord != null) {
			Item item = p.getRoom().pickItem(secondWord);
			if (item != null) {
				if (p.pickItem(item)) {
					return true;
				}
			}
		}
		return false;
	}
}
