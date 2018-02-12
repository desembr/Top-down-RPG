package server;

import java.util.ArrayList;
import java.util.List;

/*
 * Class Player - contains state for the player (current room, items etc).
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Player extends Entity {
	private static final long serialVersionUID = 1L;
	private static int damage = 30, defence = 32, num = 0;
	
	private static String name = "Player", iconFilePath = "player_no_armor_64x64.png";
	private List<Item> items;
    
    public Player()
    {
    	super(name + (++num), damage, defence, iconFilePath);
    	items = new ArrayList<>();
        //spritePath = "player_no_armor_64x64.png"; 
    }
    
    public List<Item> getItems() {
    	return items;
    }
      
    /*public String getGraphic()
    {
        return spritePath; 
    }
    
    public void setGraphic(String spritePath)
    {
        this.spritePath = spritePath; 
    }*/
}
