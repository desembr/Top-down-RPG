package server.commands;

import java.io.*;
import java.util.Hashtable;

import server.entities.Player;

/**
 * Save-player-state command.
 *
 * @author Jan Rasmussen
 * @version 2018-03-02
 */
public class Savescore extends Command {
    /**
     * Constructor for this class.
     */
    public Savescore() {
    }

    /**
     * Executes this command, sets a return message if something to write back
     * to client.
     *
     * @param p The player object which this function affects.
     * @return Whether execution of this command changed some player state.
     */
    public boolean execute(Player p) {
        p.setCmdReturnMsg("Saving score: " + p.getScore());

        // To make the runnable jar files work.
        File savesDir = new File("scores");
        if (!savesDir.exists()) {
            savesDir.mkdir();
        }

        if (secondWord != null) {
            try{
            Hashtable top10 = null;
            try {
                ;
                FileInputStream fIS = new FileInputStream(new File("scores/top10"));
                ObjectInputStream oIS = new ObjectInputStream(fIS);
                top10 = (Hashtable) oIS.readObject();
            } catch (Exception e) {
            }

            if (top10 == null) top10 = new Hashtable(10);

            top10.put(secondWord.toString(), p.getScore());

            FileOutputStream fS = new FileOutputStream(new File("scores/top10"));
            ObjectOutputStream oS = new ObjectOutputStream(fS);
            oS.writeObject(top10);
            oS.close();
            fS.close();
            return true;
        }   catch (IOException ioe){
            }

        }

        return false;
    }
}