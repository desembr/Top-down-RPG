package server.entities;

import java.io.Serializable;
import java.util.Random;

/*
 * Class Entity - base class for all entities (player, monsters etc).
 * @author  Christer Sonesson
 * @version 2018-02-28
 */
public class Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int health, damage, defence;
	protected String name, iconFilePath;
	protected boolean isDead = false;

	protected static Random rand = new Random();

	/**
	 * Constructor for Entity.
	 * 
	 * @param name
	 *            The name of this entity.
	 * @param damage
	 *            The damage of this entity.
	 * @param defence
	 *            The defence of this entity.
	 * @param health
	 *            The health of this entity.
	 * @param iconFilePath
	 *            The iconFilePath of this entity (to display).
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
	 * 
	 * @param e
	 *            The entity to attack.
	 * @return Whether attack landed, false if entity is already dead.
	 */
	public boolean attack(Entity e) {
		if (e != null) {
			if (e.getIsDead()) {
				return false;
			}
			if (rand.nextInt(e.getDefence()) <= 8)
				e.lowerHealth(damage);
		}
		return true;
	}

	/**
	 * Lower health of this entity.
	 * 
	 * @param amount
	 *            The amount to lower this entity's health.
	 */
	public void lowerHealth(int amount) {
		health -= amount;
		if (health <= 0) {
			isDead = true;
		}
	}

	/**
	 * Kills off this entity by setting isDead to true, will be removed in
	 * ClientHandler.
	 */
	public synchronized void kill() {
		isDead = true;
	}

	/**
	 * Returns if this entity is dead or alive.
	 * 
	 * @return Whether this entity is dead or not (and hence should be removed).
	 */
	public synchronized boolean getIsDead() {
		return isDead;
	}

	/**
	 * Returns health of this entity.
	 * 
	 * @return The remaining health.
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Returns damage of this entity.
	 * 
	 * @return The damage.
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * Returns damage of this entity.
	 * 
	 * @return The damage.
	 */
	public int getDefence() {
		return defence;
	}

	/**
	 * Returns name of this entity.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns iconFilePath of this entity.
	 * 
	 * @return The iconFilePath.
	 */
	public String getIconFilePath() {
		if (!isDead) {
			return iconFilePath;
		} else // only used if a monster is dead
		{
			String[] parts = iconFilePath.split("/");
			String deadPath; 
			
			if (parts.length > 3){ // low-res version
				String part1 = parts[0]; // res
				// String part2 = parts[1]; // monsters
				String part3 = parts[2]; // low-res
				String part4 = parts[3]; // filename
				
				deadPath = part1 + "/dead/" + part3 + "/" + part4;
			}
			else // high-res version
			{
				String part1 = parts[0]; // res
				// String part2 = parts[1]; // monsters
				String part3 = parts[2]; // filename
				
				deadPath = part1 + "/dead/" + part3;
			}
			
			return deadPath;
		}
	}

	/**
	 * Adds damageChange to current damage value.
	 * 
	 * @param dmgChange
	 *            The value to add to damage.
	 */
	public void changeDamage(int dmgChange) {
		damage += dmgChange;
	}

	/**
	 * Adds defChange to current damage value.
	 * 
	 * @param defChange
	 *            The value to add to defence.
	 */
	public void changeDefence(int defChange) {
		defence += defChange;
	}

	/**
	 * Sets iconFilePath of this entity
	 * 
	 * @param iconFilePath
	 *            The new iconFilePath.
	 */
	public void setIconFilePath(String iconFilePath) {
		this.iconFilePath = iconFilePath;
	}
}
