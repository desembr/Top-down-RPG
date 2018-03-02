package server.commands;

import server.entities.Player;

/**
 * Go-back-to-previous-room command.
 * 
 * @author Tom Bjurenlind
 * @version 2018-02-20
 */
public class Back extends Command {
	/**
	 * Constructor for this class.
	 */
	public Back() {
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
		if (p.goBackARoom()) {
			p.setCmdReturnMsg(this.getClass().getName());
			return true;
		}
		p.setCmdReturnMsg("You must clear all monsters first!");
		return false;
	}
}
