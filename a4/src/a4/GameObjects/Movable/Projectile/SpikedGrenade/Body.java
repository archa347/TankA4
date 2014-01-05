package a4.GameObjects.Movable.Projectile.SpikedGrenade;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Body {
	private int diameter;
	private AffineTransform translate, scale, rotate;
	
	public Body() {
		this.diameter = 10;
		
		this.translate = new AffineTransform();
		this.scale = new AffineTransform();
		this.rotate = new AffineTransform();
		
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
	
	public void draw(Graphics2D g) {
		AffineTransform saveAT = g.getTransform();
		
		g.transform(translate);
		g.transform(scale);
		g.transform(rotate);
		
		g.setColor(Color.black);
		g.fillOval(-diameter/2, -diameter/2, diameter, diameter);
		
		g.setTransform(saveAT);
	}	
	
}
