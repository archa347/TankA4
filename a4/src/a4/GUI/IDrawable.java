package a4.GUI;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Defines common interface for objects drawable in GUI
 * @author Daniel Gallegos
 *
 */
public interface IDrawable {
	/**
	 * Draws object onto a Graphics object
	 * @param g Graphics object of component displaying drawable
	 */
	public void draw(Graphics2D g);
	
}
