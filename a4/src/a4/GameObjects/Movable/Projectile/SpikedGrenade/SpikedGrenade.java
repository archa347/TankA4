package a4.GameObjects.Movable.Projectile.SpikedGrenade;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import a4.GUI.ICollider;
import a4.GameObjects.GameObject;
import a4.GameObjects.Movable.Projectile.Projectile;
import a4.GameObjects.Movable.Vehicle.Vehicle;
import a4.GameObjects.Movable.Movable;

/**
 * Defines the SpikedGrenade Projectile object
 * @author Daniel Gallegos
 *
 */
public class SpikedGrenade extends Projectile {
	
	private int diameter;
	private Spike spikes[];
	private Body body;
	private int rate;
	private float spikeRadius;
	private boolean expand;
	private GameObject attached;
	private AffineTransform offset;
	private int count;
	
	public SpikedGrenade(float x, float y, int s, int d) {
		super(x, y, Color.black, s, d, 3000, 15);
		this.diameter=15;
		
		spikes = new Spike[4];
		
		this.spikeRadius = 0;
		
		for (int i=0; i<4; i++) {
			spikes[i] = new Spike();
			spikes[i].translate(10,0);
		}
		
		spikes[1].rotate((float) Math.toRadians(90));
		spikes[2].rotate((float) Math.toRadians(180));
		spikes[3].rotate((float) Math.toRadians(270));
		
		this.expand=true;
		this.attached=null;
		
		body = new Body();
		
		rate = 1;
		count = 0;
	}

	@Override
	public void update(int time) {
		super.update(time);
		
		//If grenade is attached to an object
		if (attached != null) {
			count = count+time;
			if (attached instanceof Vehicle)
				if (count%1000==0)	//Hit once per second on a vehicle
					this.getWorld().tankHit((Vehicle) attached, this.getDmg());
			
			//set my translation AT to attached AT, rotate, translate by offset
			this.setTranslation((AffineTransform) attached.getTranslation().clone());
			this.getTranslation().concatenate(attached.getRotation());
			this.getTranslation().concatenate(this.offset);
			
		}
			
		if (this.spikeRadius >= 5) {
			this.expand=false;
		}
		
		if (this.spikeRadius <= 0) {
			this.expand=true;
		}
		
		
		/*
		 * Expand or contract spikes by translate left or right
		 * (in spike local coordinates).  If object has not attached,
		 * rotate spikes (again in local coordinates) 
		 */
		if (this.expand) {
			this.getScale().scale(1.1, 1.1);
			this.spikeRadius++;
			for (int i=0; i<4; i++) {
				spikes[i].translate(1, 0);
				if (attached == null) spikes[i].rotate((float) Math.toRadians(1));
			}
		}
		else {
			this.getScale().scale(.9, .9);
			this.spikeRadius--; 
			for (int i=0; i<4; i++) {
				spikes[i].translate(-1, 0);
				if (attached == null) spikes[i].rotate((float) Math.toRadians(1));
			}
		}
	}
	
	@Override
	public float getSize() {
		return this.diameter;
	}
	
	@Override
	public void handleCollision(ICollider o) {
		
		if (o instanceof Projectile) {
			this.setDelete(true);
			return;
		}
		
		if (attached == null) {
			this.attached = (GameObject) o;	//Grenade "attaches" to tank on collision
			this.setSpeed(0);
			
			Point2D myPoint = new Point2D.Double(0,0);
			
			AffineTransform myAT = new AffineTransform();
			
			//build my AT
			myAT.concatenate(this.getTranslation());
			myAT.concatenate(this.getScale());
			myAT.concatenate(this.getRotation());
			
			myAT.transform(myPoint, myPoint);
			
			//Build collider AT
			AffineTransform otherAT = new AffineTransform();
			
			otherAT.concatenate(attached.getTranslation());
			otherAT.concatenate(attached.getScale());
			otherAT.concatenate(attached.getRotation());
			
			//Determine my location in collider's local coordinates
			try {
				otherAT.inverseTransform(myPoint, myPoint);
			} catch (NoninvertibleTransformException e) { }
			
			this.offset = new AffineTransform();
			
			//save as a transform
			this.offset.translate(myPoint.getX(),myPoint.getY());
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		AffineTransform saveAT=g.getTransform();
			super.draw(g);
			
			
			body.draw(g);
			for (int i=0; i<4; i++) {
				spikes[i].draw(g);
			}	
		g.setTransform(saveAT);
	}
	
	

}
