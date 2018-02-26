package server.commands;

import server.entities.Player;
import server.items.Item;

/**
 * Pick-item command.
 * 
 * @author Emir Zivcic
 * @version 2018-02-28
 */
public class Pick extends Command {
	/**
	 * Constructor for this class.
	 */
	public Pick() {

	}

	/**
	 * Executes this command, sets a return message if something to write back
	 * to client.
	 * 
	 * @param p
	 *            The player object which this function affects.
	 * @return Whether execution of this command changed some player state.
	 */
	public boolean execute(Player p) {
		p.setCmdReturnMsg(this.getClass().getName());
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
