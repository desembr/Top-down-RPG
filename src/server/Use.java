 package server; 

/**
 * Use-item command.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Use extends Command {
	/**
	 * Constructor for this class.
	 */
	public Use() {
	}
	
	/**
	 * Executes this command.
	 * @param p The player object which this function affects.
	 * @return Whether execution of this command changed some player state.
	 */
	public boolean execute(Player p) {
		if (secondWord != null) {
			try {
				int itemIndex = Integer.parseInt(secondWord);
				if (p.useItem(itemIndex)) {
					return true;
				}
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return false;
	}
}
