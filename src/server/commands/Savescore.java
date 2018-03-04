package server.commands;

import java.io.*;
import java.util.*;

import server.entities.Player;

/**
 * Save p.getScore() to hashtable in score/top10 file, maps to secondWord.
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
            TreeMap<Integer, ArrayList<String>> top10 = null;
            try {
                FileInputStream fIS = new FileInputStream(new File("scores/top10"));
                ObjectInputStream oIS = new ObjectInputStream(fIS);
                top10 = (TreeMap) oIS.readObject();
            } catch (Exception e) {
            }

            if (top10 == null) top10 = new TreeMap();

            ArrayList<String> scoreHolders = top10.get(p.getScore());
            if(scoreHolders == null) scoreHolders = new ArrayList<>();
            scoreHolders.add(secondWord);

            top10.put(p.getScore(), scoreHolders);

          /*
            if (top10.get(p.getScore()) == null)  top10.put(p.getScore(), new ArrayList<String>());


            List scoreadd = (ArrayList)top10.get(p.getScore());
            scoreadd.add(secondWord);
*/
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