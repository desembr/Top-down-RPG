package server.commands;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import server.entities.Player;

/**
 * Load-player-state command.
 * 
 * @author Jan Rasmussen
 * @version 2018-02-22
 */
public class Load extends Command {

	/**
	 * Constructor for this class.
	 */
	public Load() {
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
		p.setCmdReturnMsg("Loading game...");
		if (secondWord != null) {
			try {
				FileInputStream fIS = new FileInputStream("saves/" + secondWord);
				ObjectInputStream oIS = new ObjectInputStream(fIS);
				Player player = (Player) oIS.readObject();
				boolean playerLoaded = p.loadPlayer(player);
				oIS.close();
				fIS.close();
				if (playerLoaded) {
					return true;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}
}