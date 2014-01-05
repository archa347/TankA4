package a4.GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.List;
import java.util.Vector;

import a4.IGameWorld;
import a4.GUI.ICollider;
import a4.GUI.IDrawable;

/** GameObject is the base class for all physical objects in the game. GameObject is abstract, 
 * and defines properties and methods inherent to all GameObjects
 *@author Daniel Gallegos*/
public abstract class GameObject implements IDrawable,ICollider {
	
	//private float locX;
	//private float locY;
	private Color color;
	private IGameWorld world;
	private boolean delete;
	private Vector<ICollider> colliders;
	private AffineTransform translation, rotation, scale;

	/**
	 * Primary constructor for all GameObjects to define location and color
	 * @param x X coordinate of new object
	 * @param y Y coordinate of new object
	 * @param c an object of type Color
	 * @param s integer object size
	 */
	public GameObject(float x, float y,Color c) {
		
		this.translation = new AffineTransform();
		this.rotation = new AffineTransform();
		this.scale = new AffineTransform();
		
		this.translation.translate(x, y);
		
		this.color = c;
		this.delete=false;
		
		colliders = new Vector<ICollider>();
	}
	
	/**
	 * Accessor for current location X coordinate
	 * @return float for the X coordinate of current location
	 */
	/*public float getLocX() {
		return this.locX;
	}*/
	
	/**
	 * Accessor for current location Y coordinate
	 * @return float for the Y coordinate of current location
	 */
	/*public float getLocY() {
		return this.locY;
	}*/
	
	/**
	 * Accessor for current color
	 * @return Color object of current color
	 */
	public Color getColor() {
		return this.color;
	}
	
	/**
	 * Setter for x coordinate of new location
	 * @param x float value for x coordinate of new location
	 */
	/*public void setLocX(float x) {
		if (x > this.world.getMapX()) this.locX = 0;
		else if (x < 0) this.locX = this.world.getMapX();
		else this.locX = x;
	}*/
	
	/**
	 * Setter for y coordinate of new location
	 * @param y float value of y coordinate of new location
	 */
	/*public void setLocY(float y) {
		if (y > this.world.getMapY()) this.locY = 0;
		else if (y < 0) this.locY = this.world.getMapY();
		else this.locY = y;
	}*/
	
	/**
	 * Setter for object color
	 * @param c Color object
	 */
	public void setColor(Color c) {
		this.color = c;
	}
	
	/**
	 * Returns a string representation of the Game Object
	 */
	public String toString() {
		return " loc=" + this.translation.getTranslateX() + "," + this.translation.getTranslateY() + " color=[" + this.color.getRed() + "," 
				+ this.color.getGreen() + "," + this.color.getBlue() + "]";
	}

	public IGameWorld getWorld() {
		return world;
	}

	public void setWorld(IGameWorld world) {
		this.world = world;
	}
	
	public boolean isDelete() {
		return delete;
	}
	
	/**
	 * Marks object to be removed from GameWorld
	 * @param delete boolean true if Object should be removed from 
	 */
	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	/**
	 * Updates the time dependent properties of objects
	 */
	abstract public void update(int time);

	public void draw(Graphics2D g) {
		g.transform(this.getTranslation());
		g.transform(this.getRotation());
		g.transform(this.getScale());
	}
	
	public boolean collidesWith(ICollider otherObj) {
		
		GameObject o = (GameObject) otherObj;
		
		if (otherObj == this) return false;
		
		Point2D otherPoint = new Point2D.Double(0,0);
		
		AffineTransform otherAT = new AffineTransform();
		
		//build otherObj AT
		otherAT.concatenate(o.getTranslation());
		otherAT.concatenate(o.getScale());
		otherAT.concatenate(o.getRotation());
		
		//transform otherObject origin to world coordinates
		otherAT.transform(otherPoint, otherPoint);
		
		AffineTransform myAT = new AffineTransform();
		
		//build my AT
		myAT.concatenate(this.translation);
		myAT.concatenate(this.scale);
		myAT.concatenate(this.rotation);
		
		//translate otherPoint world coordinates to my local coordinates
		try {
			myAT.inverseTransform(otherPoint, otherPoint);
		} catch (NoninvertibleTransformException e) {
			return false;
		}
		
		//If distance is greater than sum of each radii, 
		return otherPoint.distance(0,0) <= (o.getSize()/2 + this.getSize()/2); 
		
	}
	
	public void addCollider(ICollider collider) {
		colliders.add(collider);
	}
	
	public void resetColliders() {
		colliders = new Vector<ICollider>();
	}
	
	public Vector<ICollider> getColliders() {
		return this.colliders;
	}
	
	public AffineTransform getTranslation() {
		return this.translation;
	}
	
	public void setTranslation(AffineTransform at) {
		this.translation = at;
	}
	
	public AffineTransform getRotation() {
		return this.rotation;
	}
	
	public void setRotation(AffineTransform at) {
		this.rotation = at;
	}
	
	public AffineTransform getScale() {
		return this.scale;
	}
	
	public void setScale(AffineTransform at) {
		this.scale = at;
	}
	
	
	
	abstract public float getSize();
}
