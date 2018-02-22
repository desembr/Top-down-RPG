package server; /* * This class holds an enumeration of all command words known to the game. * It is used to recognize commands as they are typed in. * * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic * @version 1.0 */public class CommandWords{    // A constant array that holds all valid command words.    private static final String validCommands[] = {        "go", "save", "load", "drop", "pick", "attack", "use", "exit", "help", "look", "back"    };    /**     * Constructor - initialize the command words.     */    public CommandWords()    {        // nothing to do at the moment...    }    /**     * Check whether a given String is a valid command word.      * Return true if it is, false if it isn't.     **/    public boolean isCommand(String aString)    {        for(int i = 0; i < validCommands.length; i++) {            if(validCommands[i].equals(aString))                return true;        }        // if we get here, the string was not found in the commands        return false;    }    /*     * returns a String of all valid commands.     */    public String showAll()     {        StringBuffer commands = new StringBuffer();        for(int i = 0; i < validCommands.length; i++) {            commands.append(validCommands[i] + (((i <= 6) ? " target" : "") +             		(i < (validCommands.length - 1) ? ",  " : "")));        }        commands.append(".\nWhere target can be a direction, item name, entity name or file name, "        		+ "depending on the command.");        return commands.toString();    }}