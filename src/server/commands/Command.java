package server.commands;import server.entities.Player;/** * This class is part of the "World of Zuul" application. "World of Zuul" is a * very simple, text based adventure game. *  * This class holds information about a command that was issued by the user. * A command currently consists of two strings: a command word and a second * word (for example, if the command was "take map", then the two strings * obviously are "take" and "map"). *  * The way this is used is: Commands are already checked for being valid * command words. If the user entered an invalid command (a word that is not * known) then the command word is <null>. * * If the command had only one word, then the second word is <null>. *  * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic * @version 2018-02-28 */public class Command{    protected String secondWord = null;    /**     * Create a command object. First and second word must be supplied, but     * either one (or both) can be null. The command word should be null to     * indicate that this was a command that is not recognized by this game.     */    public Command()    {    }        /**	 * Executes this command.	 * @param p The player object which this function affects.	 * @return Whether execution of this command changed some player state.	 */	public boolean execute(Player p) {		return false;	}        /**     * Sets the option for this command (like a target for an attack).     * @param secondWord The second option/parameter of this command, set by GameEngine.     */    public void setSecondWord(String secondWord) {    	this.secondWord = secondWord;    }}