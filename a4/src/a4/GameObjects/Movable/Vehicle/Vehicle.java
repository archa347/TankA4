package a4.GameObjects.Movable.Vehicle;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import a4.IStrategy;
import a4.GUI.ICollider;
import a4.GameObjects.Movable.*;
import a4.GameObjects.Movable.Projectile.*;
import a4.GameObjects.Movable.Projectile.SpikedGrenade.SpikedGrenade;
import a4.GameObjects.Movable.Vehicle.Strategies.ConservativeStrategy;
import a4.GameObjects.Movable.Vehicle.Strategies.LiberalStrategy;

/**Vehicle represents vehicles that are controllable as player or enemies */
public abstract class Vehicle extends Movable implements ISteerable {
	private int armorStrength;
	private int missileCount;
	private boolean blocked;
	private boolean isPlayer;
	private IStrategy curStrategy;
	private int acceleration;
	private int turnRate;
	private int lastCrash;
	private int maxArmor;

	
	
	/**
	 * Basic constructor for vehicles.  All vehicles are created with speed 0, and unblocked
	 * @param x	x coordinate for starting location of vehicle
	 * @param y y coordinate for starting location of vehicle
	 * @param c color of vehicle, presented as a Color object
	 * @param d direction of the vehicle at creation
	 * @param a value of armor strength at creation
	 * @param m value of vehicle missile count at creation
	 * @param p Boolean is player vehicle
	 */
	public Vehicle(float x, float y,Color c, int d, int a, int m,boolean p) {
		
		super(x, y, c, 0, d);
		this.armorStrength=a;
		this.maxArmor = a;
		this.missileCount=m;
		this.blocked=false;
		this.isPlayer=p;
		
		Random rand = new Random();
		if (rand.nextInt(2) == 0)
			this.curStrategy = new LiberalStrategy(this);
		else this.curStrategy = new ConservativeStrategy(this);
		if (isPlayer) this.setColor(Color.BLUE);
		this.acceleration = 30;
		this.turnRate = 10;
	}
	
	/**
	 * Turns the vehicle to the left, decrementing direction by 5
	 */
	@Override
	public void turnLeft() {
		this.setDirection(this.getDirection()+turnRate);
		this.setBlocked(false);  	//changing direction unblocks vehicle, if blocked
	}
	
	/**
	 * Turns the vehicle to the right, incrementing direction by 5
	 */
	@Override
	public void turnRight() {
		this.setDirection(this.getDirection()-turnRate);
		this.setBlocked(false);		//changing direction unblocks vehicle, if blocked
	}

	/**
	 * Increases speed by 1 unit, unless vehicle is blocked
	 */
	@Override
	public void speedup() {
		if (!this.blocked) this.setSpeed(this.getSpeed() + this.acceleration);
		else System.out.println(this.toString() + "\n\t is blocked and can't change speed!");
	}
	
	/**
	 * Decrease speed by 1 unit, unless vehicle is blocked or speed is 0
	 */
	@Override
	public void slowdown() {
		if (!this.blocked) this.setSpeed(this.getSpeed() - this.acceleration);
		else System.out.println(this.toString() + "\n\t is blocked and can't change speed!");
	}
	
	
	/**
	 * Accessor for the current armor strength of the vehicle
	 * @return Integer value of current armor strength
	 */
	public int getArmorStrength() {
		return this.armorStrength;
	}
	
	/**
	 * Sets armor strength for vehicle
	 * @param a Integer value for new armor strength.  Negative values will be set as 0
	 */
	public void setArmorStrength(int a) {
		if (a > 0) this.armorStrength = a;
		else this.armorStrength = 0;
	}
	
	/**
	 * Accessor for current missile count of vehicle
	 * @return Current missile count as Integer
	 */
	public int getMissileCount() {
		return this.missileCount;
	}
	
	/**
	 * Sets missile count for vehicle
	 * @param m Integer value for new missile count. Negative values will be set as 0
	 */
	public void setMissileCount(int m) {
		if (m > 0) this.missileCount = m;
		else this.missileCount = 0;
	}
	
	/**
	 * Returns current blocked status of vehicle
	 * @return Boolean true if vehicle is blocked
	 */
	public boolean isBlocked() {
		return this.blocked;
	}
	
	/**
	 * Sets vehicle blocked status
	 * @param b Boolean true if vehicle is blocked
	 */
	public void setBlocked(boolean b) {
		
		this.blocked=b;
		if (this.blocked) this.setSpeed(0);

	}
	
	/**
	 * Moves the object, unless the object is blocked
	 */
	public void move(int time) {
		if (this.blocked) System.out.println(this.toString() + "\n\t is blocked and can't move!");
		else super.move(time);
	}
	
	/**
	 * Create a projectile object as fired by the vehicle.
	 * @return A Projectile as fired by the vehicle, null if vehicle missileCount is 0.  
	 */
	public void fire() {
		if (this.missileCount == 0) {
			System.out.println(this.toString() + "\n\t is out of missiles!");
			return;
		}
		this.missileCount--;
		Missile missile = new Missile((float)this.getTranslation().getTranslateX(), (float)this.getTranslation().getTranslateY(), this.getSpeed()+250, this.getDirection());
		
		missile.setWorld(this.getWorld());
		while (this.collidesWith(missile)) missile.move(20);
		
		this.getWorld().add(missile);
		
		this.getWorld().getSoundEffect("fire").play(this.getWorld().getSound());
		
	}
	
	/**
	 * get this vehicles current strategy
	 * @return strategy object
	 */
	public IStrategy getCurStrategy() {
		return curStrategy;
	}
	
	/**
	 * Set this objects strategy
	 * @param curStrategy
	 */
	public void setStrategy(IStrategy strategy) {
		this.curStrategy = strategy;
	}
	
	/**
	 * Registers a projectile hit on the vehicle, decrementing armor by 1
	 * @param percent Integer percentage of total life lost  
	 * @return boolean true if vehicle survives the hit, boolean false if vehicle is destroyed
	 */
	public boolean hit(int percent) {
		this.armorStrength = this.armorStrength - this.maxArmor*percent/100;
		if (this.armorStrength >= 0) return true;
		else return false;
	}
	
	
	
	
	@Override
	public void handleCollision(ICollider otherObj) {
		
		if (!(otherObj instanceof Projectile)) {
			if (this.getWorld().getClock() - lastCrash > 750) {
				this.getWorld().getSoundEffect("crash").play(this.getWorld().getSound());
				lastCrash = this.getWorld().getClock();
			}
			/*if non projectile object, undo last move 
			This prevents an object from being trapped in a colliding state by
			returning it to a non colliding state*/
			this.setDirection(this.getDirection()+180);
			this.setSpeed(10);
			this.setBlocked(false);
			while (this.collidesWith(otherObj))
				this.move(10);
			this.setBlocked(true); 
			this.setDirection(this.getDirection()+180); //restore original direction
		}
	}
	
	
	
	/**  
	 * Returns true if object is the player object
	 * @return True if object is player object, else false.
	 */
	public boolean isPlayer() {
		return isPlayer;
	}
	
	public int getAcceleration() {
		return this.acceleration;
	}
	
	/**
	 * Sets the acceleration for the vehicle
	 * @param a Integer accleration factor
	 */
	public void setAcceleration(int a) {
		this.acceleration = a;
	}
	
	
	public void update(int time) {
		super.update(time);
		if (!this.isPlayer) { 
			if (this.getWorld().getClock()/1000 % 30 == 0) {
				if (this.curStrategy instanceof LiberalStrategy)
					this.curStrategy = new ConservativeStrategy(this);
				else this.curStrategy = new LiberalStrategy(this);
			}
			
			this.curStrategy.apply();
		}
	}
	

	public String toString() {
		return super.toString() + " armor=" + this.armorStrength + " missiles=" + this.missileCount;
	}

	/**
	 * Fires a spiked grenade from this vehicle
	 */
	public void spikedGrenade() {
		if (this.missileCount == 0) {
			System.out.println(this.toString() + "\n\t is out of missiles!");
			return;
		}
		this.missileCount -= 10;
		SpikedGrenade grenade = new SpikedGrenade((float)this.getTranslation().getTranslateX(), (float)this.getTranslation().getTranslateY(), this.getSpeed()+150, this.getDirection());
		
		grenade.setWorld(this.getWorld());
		while (this.collidesWith(grenade)) grenade.move(100);
		
		this.getWorld().add(grenade);
		
		this.getWorld().getSoundEffect("fire").play(this.getWorld().getSound());
		
	}
	
	/**
	 * Fires a plasma wave from this vehicle
	 */
	public void plasmaWave() {
		if (this.missileCount == 0) {
			System.out.println(this.toString() + "\n\t is out of missiles!");
			return;
		}
		this.missileCount -= 100 ;
		PlasmaWave wave = new PlasmaWave((float)this.getTranslation().getTranslateX(), (float)this.getTranslation().getTranslateY(), this.getSpeed()+250, this.getDirection());
		
		wave.setWorld(this.getWorld());
		while (this.collidesWith(wave)) wave.move(20);
		
		this.getWorld().add(wave);
		
		this.getWorld().getSoundEffect("fire").play(this.getWorld().getSound());
		
	}

}
