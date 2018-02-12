package server; 

/*
 * Class Enemy - base class for all enemies.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public abstract class Enemy extends Entity
{   
    protected Enemy(String name, int damage, int defence, String iconFilePath) {
    	super(name, damage, defence, iconFilePath);
    }
}
