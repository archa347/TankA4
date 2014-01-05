package a4.GameObjects.Landscape;

import java.awt.Color;

import a4.GameObjects.*;


/**
 * Landscape encapsulates game objects that are part of the landscape.  
 * Landscape location and color are defined at creation and are not changeable
 * @author Daniel Gallegos
 *
 */
public abstract class Landscape extends GameObject {
	
	public Landscape(float x, float y, Color c) {
		super(x,y,c);
	}
	
	/**
	 * Landscape items may not change location and this method will print an error message
	 */
	public final void setLocX(float x) {
		System.out.println("Landscape objects may not change location");
	}
	
	/**
	 * Landscape items may not change location and this method will print an error message
	 */
	public final void setLocY(float y) {
		System.out.println("Landscape objects may not change location");
	}
	
}
