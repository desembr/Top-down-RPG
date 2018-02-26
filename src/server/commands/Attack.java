package server.commands;

import server.entities.Enemy;
import server.entities.Player;

/**
 * Attack-a-monster command.
 * 
 * @author Emir Zivcic
 * @version 2018-02-28
 */
public class Attack extends Command {
	/**
	 * Constructor for this class.
	 */
	public Attack() {
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
			// Attack enemies
			for (int i = 0; i < p.getRoom().getEnemies().size(); i++) {
				Enemy e = p.getRoom().getEnemies().get(i);
				if (e.getName().toLowerCase().equals(secondWord.toLowerCase())) {
					if (p.attack(e)) {
						return true;
					}
				}
			}
			// Attack other players
			for (int i = 0; i < p.getRoom().getPlayers().size(); i++) {
				Player pOther = p.getRoom().getPlayers().get(i);
				if (pOther.getName().toLowerCase().equals(secondWord.toLowerCase())) {
					if (p.attack(pOther)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
