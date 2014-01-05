package a4.GameObjects.Movable;

import java.awt.Color;

import a4.GUI.ICollider;
import a4.GameObjects.GameObject;

/**
 * Movable defines game objects which change position over the course of the game
 * @author Daniel Gallegos
 *
 */
public abstract class Movable extends GameObject {
	
	private int direction;
	private int speed;
	
	/**
	 * Constructor for movable objects
	 * @param x The x coordinate of the location where the object will be created
	 * @param y The y coordinate of the location where the object will be created
	 * @param c The color of the object represented by a Color object
	 * @param s The initial speed of the object.  S < 0 will be set as zero 
	 * @param d The initial direction of the object. Int value 0-360, multiple of 5
	 */
	public Movable(float x, float y, Color c, int s, int d) {
		super(x, y, c);
		int newDir = d%360+360;		//converts negative degrees to corresponding positive
		this.direction = (newDir - newDir%5) % 360; //Rounds down to multiple of 5, mods by 360 
		this.getRotation().rotate(Math.toRadians(direction));
		if (s > 0) this.speed = s;
		else this.speed = 0;
	}
	
	/**
	 * Moves the object based on its current direction, speed and elapsed time
	 */
	public void move(int time) {
		float deltaX = (float) Math.cos(Math.toRadians(this.direction)) * this.speed*time/1000;
		float deltaY = (float) Math.sin(Math.toRadians(this.direction)) * this.speed*time/1000;
		
		//this.setLocX(this.getLocX() + deltaX);
		//this.setLocY(this.getLocY() + deltaY);
		
		this.getTranslation().translate(deltaX, deltaY);
	}
	
	/**
	 * Sets the direction of the movable object. Values outside of 0-360 are modulus 360 and rounded down to nearest multiple of 5.
	 * Therefore, use multiples of 5 in range of 0-360 to ensure accuracy of change.
	 * @param d Integer direction.  
	 */
	public void setDirection(int d) {
		int newDir = d%360+360;		//converts negative degrees to corresponding positive
		int rotation = newDir - this.direction;
		this.direction = (newDir - newDir%5) % 360; //Rounds down to multiple of 5, mods by 360 
		this.getRotation().rotate(Math.toRadians(rotation));
	}
	
	/**
	 * Accessor for the current direction of the object
	 * @return direction as an integer
	 */
	public int getDirection() {
		return this.direction;
	}
	
	/**
	 * Sets the speed of the movable object
	 * @param s integer speed.  Values less than 0 will be set as 0
	 */
	public void setSpeed(int s) {
		if (s > 0)
			this.speed = s;
		else this.speed = 0;
	}
	
	/**
	 * Accessor for the current speed of the object
	 * @return speed as an integer
	 */
	public int getSpeed() {
		return this.speed;
	}
	
	public void update(int time) {
		this.move(time);
	}
	
	public void handleCollision(ICollider otherObj) {
		System.out.println("Collision Detected");
	}
	
	
	
	public String toString() {
		return super.toString() + " speed=" + this.speed + " heading=" + this.direction;
	}
	
}
