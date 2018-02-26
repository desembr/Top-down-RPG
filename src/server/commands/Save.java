package server.commands;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import server.entities.Player;

/**
 * Save-player-state command.
 * 
 * @author Jan Rasmussen
 * @version 2018-02-28
 */
public class Save extends Command {
	/**
	 * Constructor for this class.
	 */
	public Save() {
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
		p.setCmdReturnMsg("Saving game...");

		// To make the runnable jar files work.
		File savesDir = new File("saves");
		if (!savesDir.exists()) {
			savesDir.mkdir();
		}

		if (secondWord != null) {
			try {
				FileOutputStream fS = new FileOutputStream(new File("saves/" + secondWord));
				ObjectOutputStream oS = new ObjectOutputStream(fS);

				oS.writeObject(p);
				oS.close();
				fS.close();
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}
}
