package server;/** * This class is the main class of the "World of Zuul" application.  * "World of Zuul" is a very simple, text based adventure game.   * * This class holds information about a command that was issued by the user. * A command currently consists of two strings: a command word and a second * word (for example, if the command was "take map", then the two strings * obviously are "take" and "map"). *  * The way this is used is: Commands are already checked for being valid * command words. If the user entered an invalid command (a word that is not * known) then the command word is <null>. * * If the command had only one word, then the second word is <null>. *  * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic * @version 1.0 */public class Command{    //private String commandWord;    protected String secondWord = null;    protected String returnMessage = null;    /**     * Create a command object. First and second word must be supplied, but     * either one (or both) can be null. The command word should be null to     * indicate that this was a command that is not recognised by this game.     */    public Command()    {        //commandWord = firstWord;        //this.secondWord = secondWord;    }        /**	 * Executes this command.	 * @param p The player object which this function affects.	 * @return Whether execution of this command changed some player state.	 */	public boolean execute(Player p) {		return false;	}        /**     * Executes this command.     * @param secondWord The second option/parameter of this command, set by GameEngine.     */    public void setSecondWord(String secondWord) {    	this.secondWord = secondWord;    }        /**     * Return the command word (the first word) of this command. If the     * command was not understood, the result is null.     * @return The returnMessage of this command, null if this command didn't set one.     */    public String getReturnMessage()    {        return returnMessage;    }    /**     * Return the command word (the first word) of this command. If the     * command was not understood, the result is null.     */    /*public String getCommandWord()    {        return commandWord;    }*/    /**     * Return the second word of this command. Returns null if there was no     * second word.     */    /*public String getSecondWord()    {        return secondWord;    }*/    /**     * Return true if this command was not understood.     */    /*public boolean isUnknown()    {        return (commandWord == null);    }*/    /**     * Return true if the command has a second word.     */    /*public boolean hasSecondWord()    {        return (secondWord != null);    }*/}