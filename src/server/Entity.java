package server;

import java.io.Serializable;

/*
 * Class Entity - base class for all entities (player, monsters etc).
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected int health, damage, defence;
	protected String name, iconFilePath;
	protected boolean isDead = false;
    
    /**
     * Constructor for Entity.
     * @param name The name of this entity.
     * @param damage The damage of this entity.
     * @param defence The defence of this entity.
     * @param health The health of this entity.
     * @param iconFilePath The iconFilePath of this entity (to display).
     */
    protected Entity(String name, int damage, int defence, int health, String iconFilePath) {
    	this.name = name;
    	this.damage = damage;
    	this.defence = defence;
    	this.health = health;
    	this.iconFilePath = iconFilePath;
    }
    
    /**
     * Logic for attacking an entity.
     * @param e The entity to attack.
     */
    public void attack(Entity e) {
    	
    }
    
    /**
     * Lower health of this entity.
     * @param amount The amount to lower this entity's health.
     */
    public void lowerHealth(int amount) {
    	health -= amount;
    	if (health <= 0) {
    		isDead = true;
    	}
    }
    
    /**
     * Returns if this entity is dead or alive.
     * @return Whether this entity is dead or not (and hence should be removed).
     */
	public boolean getIsDead() {
    	return isDead;
    }
	
    /**
     * Returns health of this entity.
     * @return The remaining health.
     */
	public int getHealth() {
    	return health;
    }
	
	/**
     * Returns damage of this entity.
     * @return The damage.
     */
	public int getDamage() {
    	return damage;
    }
	
	/**
     * Returns damage of this entity.
     * @return The damage.
     */
	public int getDefence() {
    	return defence;
    }
    
	/**
     * Returns name of this entity.
     * @return The name.
     */
    public String getName() {
    	return name;
    }
    
    /**
     * Returns iconFilePath of this entity.
     * @return The iconFilePath.
     */
    public String getIconFilePath() {
    	return iconFilePath;
    }
    
    /**
     * Sets name of this entity
     * @param name The new name.
     */
    public void setName(String name) {
    	this.name = name;
    }
    
    /**
     * Sets damage of this entity
     * @param damage The new name.
     */
    public void setDamage(int damage) {
    	this.damage = damage;
    }
    
    /**
     * Sets defence of this entity
     * @param defence The new name.
     */
    public void setDefence(int defence) {
    	this.defence = defence;
    }
    
    /**
     * Sets iconFilePath of this entity
     * @param iconFilePath The new name.
     */
    public void setIconFilePath(String iconFilePath) {
    	this.iconFilePath = iconFilePath;
    }
}
