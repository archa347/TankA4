package a4.GameObjects.Movable.Vehicle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import a4.GUI.ICollider;
import a4.GUI.ISelectable;
import a4.GameObjects.Movable.Vehicle.Vehicle;

/**
 * Basic Tank class.  Tanks are color light grey, with 10 armor and 10 missiles
 * @author Daniel Gallegos
 *
 */
public class Tank extends Vehicle implements ISelectable {
	private int width;
	private int height;
	private boolean selected;
	
	/**
	 * Constructor for Tank class
	 * @param x X coordinate of starting location as float
	 * @param y Y coordinate of starting location as float
	 * @param d Starting direction
	 * @param p Boolean is player vehicle
	 */
	public Tank(float x, float y, int d, boolean p) {
		super(x,y,new Color(102, 102, 0),d,100,1000,p);
		this.width=30;
		this.height=30;
		this.selected=false;
	}
	
	public String toString() {
		return "Tank:" + super.toString();
	}
	
	@Override
	public void draw(Graphics2D g) {
		AffineTransform saveAT=g.getTransform();
		super.draw(g);
		
		int drawX = (int) 0 -this.height/2;
		int drawY = (int) 0 -this.width/2;
		
		if (!this.getWorld().isPaused()) this.selected = false;
		
		g.setColor(this.getColor());
		if(this.selected) g.setColor(Color.RED);
		//g.drawLine((int)this.getLocX(), (int)this.getLocY(), (int) (this.getLocX() + Math.cos(Math.toRadians(this.getDirection())) * this.height), (int) (this.getLocY() + Math.sin(Math.toRadians(this.getDirection())) * this.height));
		g.drawLine(0, 0, width, 0);
		g.drawRect(drawX, drawY, this.width, this.height);
		//g.drawString(Integer.toString(this.getArmorStrength()), 0, 0);
		
		g.setTransform(saveAT);
	}

	@Override
	public float getSize() {
		return (width);
	}

	@Override
	public void setSelected(boolean y) {
		this.selected = y;
	}

	@Override
	public boolean isSelected() {
		return this.selected;
	}

	@Override
	public boolean contains(Point2D p) {
		Point2D localPoint;
		
		//Build current AT
		AffineTransform AT = new AffineTransform();
		AT.concatenate(this.getTranslation());
		AT.concatenate(this.getScale());
		AT.concatenate(this.getRotation());
		
		
		try {
			//get inverse to translate mouse point
			AffineTransform inverseAT = AT.createInverse();
			
			localPoint = inverseAT.transform(p, null);
		} catch (NoninvertibleTransformException e) {
			return false;
		}
		
		//check if point is within object bounds
		if (Math.abs(localPoint.getX()) <= this.width/2)
			if(Math.abs(localPoint.getY()) <= this.height/2)
				return true;
				
		return false;
	}
	
	@Override
	public boolean inSelectedArea(Point2D p1, Point2D p2) {
		/*
		float minX = this.getLocX() - this.width/2;
		float maxX = this.getLocX() + this.width/2;
		
		float minY = this.getLocY() - this.height/2;
		float maxY = this.getLocY() + this.height/2;
		
		//check if either minX or maxX are within left and right boundary 
		if ((minX > left && minX < right) || (maxX > left && maxX < right))
			//repeat for y axis
			if ((minY > bottom && minY < top) || (maxY > bottom && maxY < top))
				return true; 
				*/
		
		Point2D localPoint1, localPoint2;
		
		//Build current AT
		AffineTransform AT = new AffineTransform();
		AT.concatenate(this.getTranslation());
		AT.concatenate(this.getScale());
		//AT.concatenate(this.getRotation());
		
		
		try {
			//get inverse to translate mouse point
			AffineTransform inverseAT = AT.createInverse();
			
			localPoint1 = inverseAT.transform(p1, null);
			localPoint2 = inverseAT.transform(p2, null);
		} catch (NoninvertibleTransformException e) {
			System.out.println("Noninvertible transform");
			return false;
		}
		
		if ((localPoint1.getX() > 0 && localPoint2.getX() < 0) || (localPoint1.getX() < 0 && localPoint2.getX() > 0))
			if ((localPoint1.getY() > 0 && localPoint2.getY() < 0) || (localPoint1.getY() < 0 && localPoint2.getY() > 0))
				return true;
		
		
		return false;
	}
	
}
