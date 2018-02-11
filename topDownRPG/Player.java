 

/*
 * Class Player - contains state for the player (current room, items etc).
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Player extends Entity {
    
    private String spritePath; // filnamnet för bilden som representerar spelaren
    
    
    public Player()
     {
         spritePath = "player_no_armor_64x64.png"; 
      }
      
    public String getGraphic()
    {
        return spritePath; 
    }
    
    public void setGraphic(String spritePath)
    {
        this.spritePath = spritePath; 
    }
    /*
     * Inner class Client - interfaces between player(the user) and server.
     * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
     * @version 1.0
     */
    class Client {
        
    }
}
