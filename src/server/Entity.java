 package server;

import java.io.Serializable;

/*
 * Class Entity - base class for all entities (player, monsters etc).
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int health, damage, defence;
    private String name, iconFilePath;
    
    protected Entity(String name, int damage, int defence, String iconFilePath) {
    	this.name = name;
    	this.damage = damage;
    	this.defence = defence;
    	this.iconFilePath = iconFilePath;
    }
    
    /**
     * Logic for attacking an entity.
     * @param e The entity to attack.
     */
    public void attack(Entity e) {
    	
    }
    
	public int getHealth() {
    	return health;
    }
	
	public int getDamage() {
    	return damage;
    }
	
	public int getDefence() {
    	return defence;
    }
    
    public String getName() {
    	return name;
    }
    
    public String getIconFilePath() {
    	return iconFilePath;
    }
}
