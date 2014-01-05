package a4.GameObjects.Movable.Projectile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import a4.GUI.ICollider;
import a4.GameObjects.Movable.Vehicle.Vehicle;

/**
 * Missile is a basic projectile, colored black, with lifetime 5
 * @author Daniel Gallegos
 *
 */
public class Missile extends Projectile {
	
	private int height;
	private int width;
	
	/**
	 * Constructor for Missile. Missles have a lifetime of 5 
	 * @param x X coordinate of starting location as float
	 * @param y Y coordinate of starting location as float
	 * @param s Speed as int
	 * @param d Direction as int
	 */
	public Missile(float x, float y, int s, int d) {
		super(x, y, Color.BLACK, s, d, 2000, 10);
		this.height = 10;
		this.width = 10;
	}
	
	public String toString() {
		return "Missile:" + super.toString();
	}
	
	@Override
	public void draw(Graphics2D g) {
		AffineTransform saveAT=g.getTransform();
		super.draw(g);
		
		int drawX = 0;
		int drawY = 0;
		
		g.setColor(this.getColor());
		
		int[] xPoints = { drawX - height / 2, drawX - height /2, drawX + height /2 };
		int[] yPoints = { drawY + width / 2, drawY - width /2, drawY };
		
		g.fillPolygon( xPoints, yPoints, 3);
		g.setTransform(saveAT);
	}
	
	
	@Override
	public void handleCollision(ICollider otherObj) {
		if (otherObj instanceof Vehicle)
			this.getWorld().tankHit((Vehicle) otherObj, this.getDmg());
		if (otherObj instanceof PlasmaWave) return;	//plasma waves don't affect projectiles
		this.setDelete(true);
		this.getWorld().getSoundEffect("explosion").play(this.getWorld().getSound());
	}

	@Override
	public float getSize() {
		return (width);
	}
}
