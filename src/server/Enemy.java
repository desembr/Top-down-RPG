package server; 

/*
 * Class Enemy - base class for all enemies.
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public abstract class Enemy extends Entity
{   
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor for Enemy.
	 * @param name The name of this enemy.
	 * @param weight The weight of this enemy.
	 * @param health The health of this entity.
	 * @param The iconFilePath of this enemy.
	 */
	protected Enemy(String name, int damage, int defence, int health, String iconFilePath) {
    	super(name, damage, defence, health, iconFilePath);
    }
}
