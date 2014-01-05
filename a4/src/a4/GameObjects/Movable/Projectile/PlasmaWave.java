package a4.GameObjects.Movable.Projectile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.Vector;

import a4.GUI.ICollider;
import a4.GameObjects.Movable.Vehicle.Vehicle;

/**
 * Class for the Plasma Wave weapon
 * @author Daniel Gallegos
 *
 */
public class PlasmaWave extends Projectile {
	private Vector<Point2D> controlPoints;
	private Random rnd;
	private int curveHeight;
	private int curveWidth;
	private int polyLineX[];
	private int polyLineY[];
	
	
	/**
	 * Construct a new plasma wave
	 * @param x
	 * @param y
	 * @param s Integer speed
	 * @param d Integer direction 0-360
	 */
	public PlasmaWave(float x, float y,  int s, int d) {
		super(x, y, Color.magenta, s, d, 4000, 110);
		
		rnd = new Random();
		controlPoints = new Vector<Point2D>();
		
		curveHeight = rnd.nextInt(150) + 100;
		curveWidth = rnd.nextInt(150) + 50;
		
		
		
		
		/*//Build control points for curve
		controlPoints.add(0, new Point2D.Double(-curveWidth/2,curveHeight/2));
		controlPoints.add(1, new Point2D.Double(curveWidth/2,curveHeight/4));
		controlPoints.add(2, new Point2D.Double(curveWidth/2,-curveHeight/4));
		controlPoints.add(3, new Point2D.Double(-curveWidth/2,-curveHeight/2));*/
		
		
		
		//Build control points for curve
		controlPoints.add(0, new Point2D.Double(0,curveHeight/2));
		controlPoints.add(1, new Point2D.Double(rnd.nextDouble()*-curveWidth,rnd.nextDouble()*curveHeight/2));
		controlPoints.add(2, new Point2D.Double(rnd.nextDouble()*curveWidth,rnd.nextDouble()*-curveHeight/2));
		controlPoints.add(3, new Point2D.Double(0,-curveHeight/2));
		
		// Arrays for drawing wave hull
		int arrayX[] = {(int) controlPoints.get(0).getX(), (int) controlPoints.get(1).getX(), (int) controlPoints.get(2).getX(), (int) controlPoints.get(3).getX() };
		polyLineX = arrayX;
		int arrayY[] = {(int) controlPoints.get(0).getY(), (int) controlPoints.get(1).getY(), (int) controlPoints.get(2).getY(), (int) controlPoints.get(3).getY() };
		polyLineY = arrayY;
	}

	@Override
	public float getSize() {
		return (curveHeight+curveWidth)/2;
	}
	
	@Override
	public void handleCollision(ICollider otherObj) {
		if (otherObj instanceof Vehicle) {	
			this.getWorld().tankHit((Vehicle) otherObj, this.getDmg());
			this.setDelete(true);
			this.getWorld().getSoundEffect("explosion").play(this.getWorld().getSound());
		}
	}
	
	public void draw(Graphics2D g) {
		AffineTransform saveAT=g.getTransform();
		super.draw(g);
		
		//draw curve
		g.setColor(this.getColor());
		drawBezierCurve(g, controlPoints,0);
		
		//draw convex hull
		g.setColor(Color.GREEN);
		g.drawPolyline(polyLineX, polyLineY, 4);
		g.setTransform(saveAT);
	}
	
	
	/**
	 * Draw a section of the Bezier curve using a recursive algorithm
	 * @param g	Graphics2D object
	 * @param q	Control points defining the curve
	 * @param level Max depth of recursive calls
	 */
	private void drawBezierCurve(Graphics2D g, Vector<Point2D> q, int level) {
		
		if (straightEnough(q) || level > 10)
			g.drawLine((int)q.get(0).getX(), (int)q.get(0).getY(), (int) q.get(3).getX(), (int) q.get(3).getY());
		
		else {
			Vector<Point2D> l = new Vector<Point2D>();
			Vector<Point2D> r = new Vector<Point2D>();
			
			subdivideCurve(q, l, r);
			drawBezierCurve(g,l,level+1);
			drawBezierCurve(g,r,level+1);
		}
		
	}
	
	
	
	/**
	 * Subdivides a Bezier curve into two smaller curves.  
	 * @param v Vector of Curve control points
	 * @param l Empty vector for left subdivision control points
	 * @param r Empty vector for right subdivision control points
	 */
	private void subdivideCurve (Vector<Point2D> v,Vector<Point2D> l,Vector<Point2D> r) {
		
		r.add(0,null);
		r.add(1,null);
		r.add(2,null);
		
		//L0 = V0
		l.add(0,v.get(0));
		
		//L1 = (V0 + V1) / 2
		Point2D next = new Point2D.Double((v.get(0).getX() + v.get(1).getX())/2 , (v.get(0).getY() + v.get(1).getY())/2);
		l.add(1,next);
		
		//L2 = L1/2 + (V1 + V2)/4
		next = new Point2D.Double(l.get(1).getX()/2 + (v.get(1).getX() + v.get(2).getX())/4 , l.get(1).getY()/2 + (v.get(1).getY() + v.get(2).getY())/4 );
		l.add(2, next);
		
		//R3 = V3
		r.add(3,v.get(3));
		
		//R2 = (V2 + V3)/2 
		next = new Point2D.Double((v.get(2).getX() + v.get(3).getX())/2 , (v.get(2).getY() + v.get(3).getY())/2);
		r.remove(2);
		r.add(2,next);
		
		//R1 = (V1+V2)/4 + R2/2
		next = new Point2D.Double((v.get(1).getX()+v.get(2).getX())/4 + r.get(2).getX()/2, (v.get(1).getY()+v.get(2).getY())/4 + r.get(2).getY()/2 );
		r.remove(1);
		r.add(1,next);
		
		//L3 = (L2 + R1)/2
		next = new Point2D.Double((l.get(2).getX() + r.get(1).getX())/2, (l.get(2).getY() + r.get(1).getY())/2 );
		l.add(3,next);
		
		//R0 = L3
		r.remove(0);
		r.add(0,l.get(3));
	}
	
	/**
	 * Determines the co-linearity of the control points
	 * @param v Vector of control points for a Bezier curve
	 * @return true if the vector is within .001 of being straight
	 */
	private boolean straightEnough(Vector<Point2D> v) {
		double d1 = v.get(0).distance(v.get(1)) + v.get(1).distance(v.get(2)) + v.get(2).distance(v.get(3));
		double d2 = v.get(0).distance(v.get(3));
		
		if (Math.abs(d1-d2) < .001 )
			return true;
		else return false;
		
	}
	
	
}
