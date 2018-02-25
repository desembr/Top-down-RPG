package server.commands;

import server.Room;
import server.entities.Player;

/**
 * Display-info-of-current-room-or-adjacent-room command.
 * 
 * @author Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 2018-02-28
 */
public class Look extends Command {
	/**
	 * Constructor for this class.
	 */
	public Look() {
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
		if (secondWord != null) {
			Room r = p.getRoom().getExit(secondWord);
			if (r != null)
				p.setCmdReturnMsg(p.getRoom().getExit(secondWord).getPeekDescription());
			else
				p.setCmdReturnMsg(null);
		} else
			p.setCmdReturnMsg(p.getRoom().getLongDescription());

		return true;
	}
}
