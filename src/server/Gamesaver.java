package server;

import java.io.*;

public class Gamesaver extends Command {

    public Gamesaver(){}

    public boolean execute(Player p){
        if (secondWord != null){
            try {
                FileOutputStream fS = new FileOutputStream(new File(secondWord));
                ObjectOutputStream oS = new ObjectOutputStream(fS);

                oS.writeObject(p);
                oS.close();
                fS.close();
                System.out.println("Saved game! //GameSaver");
                return true;
            } catch (Exception e) {
                //System.out.println(e.getStackTrace());
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
