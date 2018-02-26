package server.commands;

import server.entities.Player;

/**
 * Exit-game command.
 * 
 * @author Jan Rasmussen
 * @version 2018-02-28
 */
public class Exit extends Command {
	/**
	 * Constructor for this class.
	 */
	public Exit() {
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
		p.setCmdReturnMsg("Thanks for playing, exiting game...");
		p.kill();
		return true;
	}
}
