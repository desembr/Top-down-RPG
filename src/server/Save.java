 package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Save-player-state command.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Save extends Command {
	/**
	 * Constructor for this class.
	 */
	public Save() {
	}
	
	/**
	 * Executes this command, sets a return message if something to write back to client.
	 * @param p The player object which this function affects.
	 * @return Whether execution of this command changed some player state.
	 */
	public boolean execute(Player p) {
		p.setCmdReturnMsg(this.getClass().getName());
		if (secondWord != null){
            try {
            	FileOutputStream fS = new FileOutputStream(new File("saves/" + secondWord));
                ObjectOutputStream oS = new ObjectOutputStream(fS);

                oS.writeObject(p);
                oS.close();
                fS.close();
                System.out.println("Saved game!");
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } return false;
	}
	
	/*

    public void saveGame(Player p) {

        try {
            FileOutputStream fS = new FileOutputStream(new File("savedGame.data"));
            ObjectOutputStream oS = new ObjectOutputStream(fS);

            oS.writeObject(p);
            oS.close();
            fS.close();
            System.out.println("Saved game! //GameSaver");
        } catch (Exception e) {
            //System.out.println(e.getStackTrace());
        }
    }
        public void loadPlayerState() throws ClassNotFoundException {
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
    }

*/
}
