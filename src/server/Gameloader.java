package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Gameloader extends Command{

    public Gameloader(){}

    public boolean execute(Player p){
        if (secondWord != null){
            try {
                FileInputStream fIS = new FileInputStream("" + secondWord);
                ObjectInputStream oIS = new ObjectInputStream(fIS);
                Player playur = (Player)oIS.readObject();
                boolean playerLoaded = p.loadPlayer(playur);
                if (playerLoaded){ return true; }

                //oIS.close();
               //fIS.close();

            } catch (Exception e) {
                //System.out.println(e.getStackTrace());
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