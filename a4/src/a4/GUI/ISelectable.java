package a4.GUI;


import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 * This class defines objects that are selectable in the game when it is paused
 * @author Daniel Gallegos
 */
public interface ISelectable {
	/**
	 * Marks object selected or not
	 * @param y Boolean true for selected
	 */
	public void setSelected(boolean y);
	
	/**
	 * Returns selected state of object
	 * @return True if selected
	 */
	public boolean isSelected();
	
	/**
	 * Checks if a selected point contains an object
	 * @param locX X coordinate of point
	 * @param locY Y coordinate of point
	 * @return true if point is contained in object
	 */
	public boolean contains(Point2D p);
	
	public boolean inSelectedArea(Point2D p1, Point2D p2);
	
	/**
	 * Draws the object
	 * @param g Graphics object from Java GUI component
	 */
	public void draw(Graphics2D g);
	
}
