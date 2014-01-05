package a4.GameObjects.Movable;
/**
 * The Steerable interface defines methods for Game Objects that are able to change their speed and direction
 * @author Daniel Gallegos
 *
 */
public interface ISteerable {
	/**
	 * Turns the object to the left
	 */
	public void turnLeft();
	
	/**
	 * Turns the object to the right
	 */
	public void turnRight();
	
	/**
	 * Increases the speed of the object
	 */
	public void speedup();
	
	/**
	 * Decreases the speed of the object
	 */
	public void slowdown();
}
