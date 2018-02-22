package server;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Load-player-state command.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Load extends Command{

	/**
	 * Constructor for this class.
	 */
    public Load(){}

    /**
	 * Executes this command, sets a return message if something to write back to client.
	 * @param p The player object which this function affects.
	 * @return Whether execution of this command changed some player state.
	 */
    public boolean execute(Player p){
        if (secondWord != null){
            try {
            	FileInputStream fIS = new FileInputStream("saves/" + secondWord);
                ObjectInputStream oIS = new ObjectInputStream(fIS);
                Player player = (Player)oIS.readObject();
                boolean playerLoaded = p.loadPlayer(player);
                oIS.close();
                fIS.close();
                if (playerLoaded){ return true; }  
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        } return false;
    }
}

 /*   public void loadPlayerState() throws ClassNotFoundException {
        try {
            FileInputStream fIS = new FileInputStream("savedGame.data");
            ObjectInputStream oIS = new ObjectInputStream(fIS);
            Player playur = (Player)oIS.readObject();
            //GameServer.ch.loadPlayer(playur);

        } catch (Exception e){
            System.out.println("Could not LOAD");
        }
        //return new Player(new Room("in a room you failed to load", "res/rooms/dungeon_W.png"));
    }
    public Player loadGameEngineState() throws ClassNotFoundException {
        try {
            FileInputStream fIS = new FileInputStream("savedGame.data");
            ObjectInputStream oIS = new ObjectInputStream(fIS);

            return ((Player) oIS.readObject());

        } catch (Exception e){
            System.out.println("Could not LOAD");
        }
        return new Player(new Room("in a room you failed to load", "res/rooms/dungeon_W.png"));
    }*/