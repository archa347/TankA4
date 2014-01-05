package a4.GameObjects.Movable.Projectile;

import java.awt.Color;

import a4.GameObjects.Movable.Movable;

/**
 * Projectiles are movable objects.  Their speed and direction are not changeable after creation, and they typically have fixed
 * lifetimes after which they are destroyed
 * @author Dan
 *
 */
public abstract class Projectile extends Movable {

	private int lifetime;
	private int dmg;
	
	/**
	 * This constructor should be invoked by all Projectile objects
	 * @param x X coordinate of starting location as float
	 * @param y Y coordinate of starting location as float
	 * @param c Color of Projectile, as Color object
	 * @param s Speed of projectile as integer
	 * @param d Direction of projectile, as integer
	 * @param l Lifetime of projectile in milliseconds, as integer
	 */
	public Projectile(float x, float y, Color c, int s, int d, int l, int dmg) {
		super(x, y, c, s, d);
		this.lifetime=l;
		this.setDmg(dmg);
	}
	
	/**
	 * Attempting to change projectile direction will generate an error
	 */
	public void setDirection(int d) {
		System.out.println("Projectiles cannot change direction!");
	}
	
	/**
	 * Attempting to change projectile speed will generate an error
	 */
	//public void setSpeed(int s) {
		//System.out.println("Projectiles cannot change speed!");
	//}
	
	/**
	 * Accessor for projectile's current lifetime
	 * @return Projectile lifetime as integer
	 */
	public int getLifetime() {
		return this.lifetime;
	}
	
	/**
	 * Set projectile lifetime
	 * @param New projectile lifetime as integer.  Negative values will be set to zero
	 */
	public void setLifetime(int l) {
		if (l >= 0) this.lifetime=l;
		else this.lifetime = 0;
	}
	
	/**
	 * Decrements the projectile lifetime
	 * @return Boolean true if the projectile is still live after decrement, false if lifetime expired
	 */
	public boolean decrementLife(int time) {
		this.lifetime=this.lifetime-time;
		if (this.lifetime <= 0) return false;
		else return true;
	}
	
	
	public void update(int time) {
		super.update(time);
		this.decrementLife(time); 
	}
	
	public String toString() {
		return super.toString() + " lifetime:" + this.lifetime;
	}

	public int getDmg() {
		return dmg;
	}

	public void setDmg(int dmg) {
		this.dmg = dmg;
	}

}
