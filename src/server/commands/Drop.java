package server.commands;

import server.entities.Player;

/**
 * Drop-item command.
 * 
 * @author Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 2018-02-28
 */
public class Drop extends Command {
	/**
	 * Constructor for this class.
	 */
	public Drop() {
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
			if (p.dropItem(secondWord)) {
				return true;
			}
		}
		return false;
	}
}
