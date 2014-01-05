package a4.GameObjects.Landscape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import a4.GUI.ICollider;
/**
 * Trees are landscape objects.  They have a diameter.
 * @author Daniel Gallegos
 *
 */
public class Tree extends Landscape {
	private int diameter;
	
	/**
	 * Constructor for new Tree objects
	 * 
	 * @param x the x coordinate of location for new Tree object
	 * @param y the y coodrinate of location for new Tree object
	 * @param d the diameter of new Tree object, between 1 and 20.  Less than 1 will default to 1, greater than 20 will default to 20
	 */
	public Tree(float x, float y, int d) {
		super(x, y, new Color(76,153,0));
		
		this.diameter = d;
	}
	
	/**
	 * Accessor for diameter of Tree object
	 * @return
	 */
	public int getDiameter() {
		return this.diameter;
	}
	
	public void update(int time) {
		
	}
	
	public String toString() {
		return "Tree:" + super.toString() + " diameter=" + this.diameter; 
	}
	
	@Override
	public void draw(Graphics2D g) {
		AffineTransform saveAT=g.getTransform();
		super.draw(g);
		int drawX = (int) 0 - this.getDiameter()/2;
		int drawY = (int) 0 - this.getDiameter()/2;
		
		g.setColor(this.getColor());
		g.fillOval(-this.diameter/2, -this.diameter/2, this.diameter, this.diameter);
	
		g.setTransform(saveAT);
	}

	@Override
	public void handleCollision(ICollider otherObj) {
		
	}

	@Override
	public float getSize() {
		return this.diameter;
	}
}