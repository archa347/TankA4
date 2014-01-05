package a4.GameObjects.Landscape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import a4.GUI.ICollider;
import a4.GameObjects.GameObject;

/**
 * Rocks are landscape objects.  They have a width and height
 * @author Daniel Gallegos
 */
public class Rock extends Landscape {
	private int height;
	private int width;
	
	/**
	 * Constructor for Rock
	 * @param x The x coordinate of location of new Rock object
	 * @param y The y coordinate of location of new Rock object
	 * @param h The height of the new Rock object, between 1 and 20.  Less than 1 will default to 1, greater than 20 will default to 20
	 * @param w The width of the new Rock object,  between 1 and 20.  Less than 1 will default to 1, greater than 20 will default to 20
	 */
	public Rock(float x, float y, int h, int w){
		super(x,y, Color.GRAY);
	
		this.height=h;
		this.width=w;
	}
	
	/**
	 * Accessor for height of current object
	 * @return height of Rock object
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * Accessor for width
	 * @return width of Rock object
	 */
	public int getWidth() {
		return this.width;
	}
	
	public String toString() {
		return "Rock:"+ super.toString() + " width=" + this.width + " height=" + this.height;
	}
	
	public void update(int time) {
		
	}

	@Override
	public void draw(Graphics2D g) {
		AffineTransform saveAT=g.getTransform();
		
		super.draw(g);
		
		g.setColor(this.getColor());
		int drawX = (int) 0 - this.getWidth()/2;
		int drawY = (int) 0 - this.getHeight()/2;
		g.fillRect(drawX, drawY, this.getWidth(), this.getHeight());
		
		g.setTransform(saveAT);
	}

	
	
	@Override
	public void handleCollision(ICollider otherObj) {
		
	}

	@Override
	public float getSize() {
		return (width + (height-width)/(float) 2.0);
	}



}
