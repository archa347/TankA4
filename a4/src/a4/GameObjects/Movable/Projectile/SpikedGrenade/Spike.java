package a4.GameObjects.Movable.Projectile.SpikedGrenade;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * Implements the spikes of the Spike Grenade projectile objects
 * @author Daniel Gallegos
 *
 */
public class Spike {
	private AffineTransform translate, scale,rotate;
	
	private int height, width;
	
	public Spike() {
		this.translate = new AffineTransform();
		this.scale = new AffineTransform();
		this.rotate = new AffineTransform();
		
		height = 6;
		width = 10;
	}
	
	public void translate(float x, float y) {
		this.translate.translate(x, y);
	}
	
	public void scale(float x, float y) {
		this.scale.scale(x, y);
	}
	
	public void rotate(float theta) {
		this.rotate.rotate(theta);
	}
	
	/**
	 * draws the Spike
	 * @param g Graphics2D object from component
	 */
	public void draw(Graphics2D g) {
		AffineTransform saveAT = g.getTransform();
		
		g.transform(scale);
		g.transform(rotate);
		g.transform(translate);
		
		g.setColor(Color.black);
		int xPoints[] = {-width/2, width/2, -width/2};
		int yPoints[] = {height/2, 0, -height/2};
		
		g.drawPolygon(xPoints, yPoints, 3);
		
		g.setTransform(saveAT);
	}	
	
}
