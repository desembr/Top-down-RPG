 package server; 

/**
 * Print-help-to-a-user command.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Help extends Command {
	/**
	 * Constructor for this class.
	 */
	public Help() {
	}
	
	/**
	 * Executes this command.
	 * @param p The player object which this function affects.
	 * @return Always false to signal to GameEngine that a help message
	 * should be sent back to the initiating client/user.
	 */
	public boolean execute(Player p) {
		p.setCmdReturnMsg(printHelp());
		return true;
	}
	
	/**
	 * Print out some help information. Here we print some stupid, cryptic
	 * message and a list of the command words.
	 * @return The help string.
	 */
	private String printHelp() {
		return "You are lost. You are alone. You wander\naround the Maze.\n" + 
				"Your command words are: " + Parser.getParser().showCommands();
	}
}
