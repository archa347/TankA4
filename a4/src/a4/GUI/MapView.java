package a4.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

import a4.IGameWorld;
import a4.IObservable;
import a4.IObserver;
import a4.GameObjects.GameObject;
/**
 * MapView holds the graphical display elements of the game.  It also contains the Key Bindings for keyboard commands and mouse listener functions
 * @author Daniel Gallegos
 *
 */
public class MapView extends JPanel implements IObserver, MouseListener, MouseMotionListener, MouseWheelListener {
	
	private IGameWorld gameWorld;
	private Point prevPoint;
	private Point nextPoint;
	
	private int selectorLeftBorder;
	private int selectorRightBorder;
	private int selectorTopBorder;
	private int selectorBottomBorder;
	
	private AffineTransform worldND, ndScreen;
	
	private float worldLeft, worldRight, worldBottom, worldTop;
	
	private JLabel myLabel;
	
	public MapView(Map<String,Action> commands, IGameWorld gw) {
		this.setBorder(new LineBorder(Color.black, 2));
		this.setBackground(Color.white);
		
		prevPoint = new Point(0,0);
		nextPoint = new Point(0,0);
		
		selectorLeftBorder=0;
		selectorRightBorder=0;
		selectorTopBorder=0;
		selectorBottomBorder=0;
		
		gameWorld = gw;
		
		//Build keyboard bindings
		KeyStroke eKey = KeyStroke.getKeyStroke('e');
		KeyStroke spaceKey = KeyStroke.getKeyStroke("SPACE");
		KeyStroke leftKey = KeyStroke.getKeyStroke("LEFT");
		KeyStroke rightKey = KeyStroke.getKeyStroke("RIGHT");
		KeyStroke downKey = KeyStroke.getKeyStroke("DOWN");
		KeyStroke upKey = KeyStroke.getKeyStroke("UP");
		KeyStroke wKey = KeyStroke.getKeyStroke('w');
		KeyStroke aKey = KeyStroke.getKeyStroke('a');
		KeyStroke dKey = KeyStroke.getKeyStroke('d');
		KeyStroke sKey = KeyStroke.getKeyStroke('s');
		KeyStroke gKey = KeyStroke.getKeyStroke('g');
		KeyStroke pKey = KeyStroke.getKeyStroke('p');
		
		//Keyboard bindings for turn right
		this.getActionMap().put("Turn Right", commands.get("r"));
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(rightKey, "Turn Right");
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(dKey, "Turn Right");
		
		//Keyboard bindings for turn left
		this.getActionMap().put("Turn Left", commands.get("l"));
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(leftKey, "Turn Left");
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(aKey, "Turn Left");
		
		//Keyboard bindings for speed up
		this.getActionMap().put("Speed Up", commands.get("i"));
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(upKey, "Speed Up");
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(wKey, "Speed Up");
		
		//Keyboard bindings for slow down
		this.getActionMap().put("Slow Down", commands.get("k"));
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(downKey, "Slow Down");
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(sKey, "Slow Down");
		
		//Keyboard bindings for fire
		this.getActionMap().put("Fire", commands.get("f"));
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(spaceKey, "Fire");
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "Fire");
		
		//Keyboard bindings for enemy switch
		this.getActionMap().put("Enemy Switch", commands.get("e"));
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(eKey, "Enemy Switch");
		
		//Keyboard binding for SpikedGrenade
		this.getActionMap().put("Spiked Grenade", commands.get("g"));
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(gKey,"Spiked Grenade");
		
		//Keyboard binding for plasma wave
		this.getActionMap().put("Plasma Wave", commands.get("p"));
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(pKey, "Plasma Wave");
		
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		
		this.worldLeft = 0;
		this.worldBottom = 0;
		this.worldRight = 1500 ;
		this.worldTop = 1500*703/893;
		
		
		//get location of player and center screen
		try {
			Point2D p = new Point2D.Double(0,0);
			p = gameWorld.getPlayer().getTranslation().inverseTransform( p,p);
			
			float worldWidth = worldRight - worldLeft;
			float worldHeight = worldTop - worldBottom;
			
			p = new Point2D.Double(p.getX() + worldWidth/2, p.getY() + worldHeight/2);
			
			
			panWindow(new Point(0,0), p );
			//zoomWindow(-2);
			
		} catch (NoninvertibleTransformException e) {
			//if invert fails, don't pan
		}
		
	}
	
	/**
	 * Updates the MapView panel
	 */
	@Override
	public void update(IObservable obj, Object arg) {
		System.out.println("Map view has been updated");
		//System.out.println(this.getSize());
		
		this.gameWorld = (IGameWorld) obj;
		this.repaint();
	}
	
	/**
	 * Overrides paintComponent to draw gameworld objects on screen
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		AffineTransform saveAT = ((Graphics2D) g).getTransform();
		AffineTransform VTM = buildVTM();
		
		((Graphics2D) g).transform(VTM);
		
		for (IDrawable next : this.gameWorld) {
			next.draw((Graphics2D) g);
		}
		
		((Graphics2D) g).setTransform(saveAT);
		
		if(gameWorld.isPaused()) {
			//Paint selection rectangle if unpaused
			g.setColor(Color.BLACK);
			g.drawRect(selectorLeftBorder, selectorBottomBorder, selectorRightBorder - selectorLeftBorder, selectorTopBorder-selectorBottomBorder);
		}
	}
	
	/**
	 * Creates the transform for World to Normalized Device
	 * @return new AT
	 */
	private AffineTransform buildWorldND() {
		AffineTransform newWorldND = new AffineTransform();
		
		AffineTransform scale = new AffineTransform();
		scale.scale(1 / (this.worldRight - this.worldLeft), 1/(this.worldTop - this.worldBottom));
		
		AffineTransform translate = new AffineTransform();
		translate.translate((-this.worldLeft), (-this.worldBottom));
		
		newWorldND.concatenate(scale);
		newWorldND.concatenate(translate);
		
		return newWorldND;
	}
	
	/**
	 * Creates transform for Normalized device to screen(this panel)
	 * @return new AT
	 */
	private AffineTransform buildNDScreen() {
		AffineTransform newNDScreen = new AffineTransform();
		
		newNDScreen.translate(0, this.getSize().getHeight());
		newNDScreen.scale(this.getSize().getWidth(), -this.getSize().getHeight());
		
		return newNDScreen;
	}
	
	private AffineTransform buildVTM() {
		AffineTransform VTM;
		
		this.worldND = buildWorldND();
		this.ndScreen = buildNDScreen();
		
		VTM = (AffineTransform) ndScreen.clone();
		VTM.concatenate(worldND);
		
		return VTM;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
		nextPoint = arg0.getPoint();
		
		if(gameWorld.isPaused()) {
			
			//Determines which point is origin of rectangle for drawing 
			if (nextPoint.x > prevPoint.x) {
				selectorLeftBorder = prevPoint.x;
				selectorRightBorder = nextPoint.x;
			}
			else {
				selectorLeftBorder = nextPoint.x;
				selectorRightBorder = prevPoint.x;
			}
			
			if (nextPoint.y > prevPoint.y) {
				selectorBottomBorder = prevPoint.y;
				selectorTopBorder = nextPoint.y;
			}
			else {
				selectorBottomBorder = nextPoint.y;
				selectorTopBorder = prevPoint.y;
			}
			
			try {
				//convert mouse coordinates to world coordinates
				Point2D prevWorldPoint = getMouseWorldLocation(prevPoint);
				Point2D nextWorldPoint = getMouseWorldLocation(nextPoint);
			
				//Go through every object and check if it is contained in the selected area
				for (GameObject o : gameWorld) {
					if (o instanceof ISelectable)
						if (((ISelectable) o).inSelectedArea(prevWorldPoint, nextWorldPoint))
							((ISelectable) o).setSelected(true);
						else ((ISelectable) o).setSelected(false);
				}
				
				this.repaint();		//repaint with changes
			
			} catch (NoninvertibleTransformException e) {
				return;
			}
		}
		
		else {
			
			try {
				//convert mouse coordinates to world coordinates
				Point2D prevWorldPoint = getMouseWorldLocation(prevPoint);
				Point2D nextWorldPoint = getMouseWorldLocation(nextPoint);
				
				panWindow(prevWorldPoint,nextWorldPoint);
				
			} catch (NoninvertibleTransformException e) {
				return;
			}
			
			this.repaint();		//repaint with changes
			
			this.prevPoint = this.nextPoint;
		}
	}
	

	

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		//check every object and determine if it has been clicked
		this.prevPoint=arg0.getPoint();
		
		try {
			Point2D worldPoint = getMouseWorldLocation(prevPoint);
		
		
		for (GameObject o : gameWorld)
			if (o instanceof ISelectable)
				if (((ISelectable) o).contains(worldPoint))
						((ISelectable) o).setSelected(true);
				else ((ISelectable) o).setSelected(false);  //unclicked objects should be unselected if previously selected
		} catch (NoninvertibleTransformException e) {
			return;
		}
		
		this.prevPoint = new Point(0,0); //reset for next click
		
		this.repaint();					//repaint with changes
		
		
		
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		//Save point in case of drag
		prevPoint = arg0.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	
		//Reset when mouse released
		prevPoint = new Point(0,0);
		nextPoint = new Point(0,0);
		selectorLeftBorder=0;
		selectorRightBorder=0;
		selectorTopBorder=0;
		selectorBottomBorder=0;
		this.repaint();
	}
	

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		Point p = arg0.getPoint();
		
		/*try {
			Point2D p2d = getMouseWorldLocation(p);
			this.myLabel.setText(p2d.getX() + "," + p2d.getY());
		} catch (NoninvertibleTransformException e) {
			return;
		}*/
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		int clicks = arg0.getWheelRotation();
		
		zoomWindow(clicks);
		
		
		this.repaint();
		
	}
	
	
	/**
	 * Converts screen coordinates to world coordinates
	 * @param p	Point current mouse location
	 * @return	Point2D world coordinates
	 * @throws NoninvertibleTransformException
	 */
	public Point2D getMouseWorldLocation(Point2D p) throws NoninvertibleTransformException {
		AffineTransform VTM, inverseVTM;
		
		VTM = buildVTM();
		
		try { 
			inverseVTM = VTM.createInverse();
		} catch (NoninvertibleTransformException e) {
			
			throw e;
		}
				
		return inverseVTM.transform(p, null);
		
	}
	
	private void panWindow(Point2D prev, Point2D next) {
		
		//computer distance of last move
		float deltaX = (float) (prev.getX() - next.getX());
		float deltaY = (float) (prev.getY() - next.getY());
		
		//Adjust world view boundaries
		this.worldLeft += deltaX;
		this.worldRight += deltaX;
		
		this.worldBottom += deltaY;
		this.worldTop += deltaY;
			
	}
	
	private void zoomWindow(int steps) {
		
		
		float width = worldRight - worldLeft;
		float height = worldTop - worldBottom;
		
		/*float centerX = (float) p.getX();
		float centerY = (float) p.getY();*/
		
		float centerX = worldLeft + width/2;
		float centerY = worldBottom + height/2;
		
		float scale = (float) (1 + ((float) steps) *.1);
		
		width *= scale;
		height *= scale;
		
		worldLeft = centerX - width/2;
		worldRight = worldLeft + width;
		worldBottom = centerY - height/2;
		worldTop = worldBottom + height;
		
		/*worldLeft = centerX - width/2;
		worldRight = centerX + width/2;
		worldBottom = centerY - height/2;
		worldTop = centerY + height/2;*/
		
		/*this.myLabel.setText(p.toString() + "\n" +
				translateX + "," + translateY + "," + scale  );
		this.myLabel.setVisible(true);*/
	}
	
}
