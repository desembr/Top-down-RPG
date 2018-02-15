 package server; 

/**
 * Attack-a-monster command.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Attack extends Command {
	/**
	 * Constructor for this class.
	 */
	public Attack() {
	}
	
	/**
	 * Executes this command, sets a return message if something to write back to client.
	 * @param p The player object which this function affects.
	 * @return Whether execution of this command changed some player state.
	 */
	public boolean execute(Player p) {
		if (secondWord != null) {
			for (int i = 0; i < p.getRoom().getEnemies().size(); i++) {
				Enemy e = p.getRoom().getEnemies().get(i);
				if (e.getName().toLowerCase().equals(secondWord.toLowerCase())) {
					p.attack(e);
					return true;
				}
			}
		}
		return false;
	}
}
