package a4.GUI;

import java.util.List;

/**	
 * Interface for game objects that can collide with other graphical objects
 * @author dan
 *
 */
public interface ICollider {
	/**
	 * Checks if this object collides with another
	 * @param otherObj Other collidable object
	 * @return true if collision occurs
	 */
	public boolean collidesWith(ICollider otherObj);
	
	/**
	 * Tell other object to handle collision
	 * @param otherObj
	 */
	public void handleCollision(ICollider otherObj);
	
	/**
	 * Returns the size of a potentially colliding object
	 * @return float size of the object
	 */
	public float getSize();
	
	/**
	 * Adds to the list of objects this object is colliding with
	 * @param collider
	 */
	public void addCollider(ICollider collider);
	
	
	/**
	 * Resets the list of objects this object is colliding with
	 */
	public void resetColliders();
	
	
	/**
	 * Provide a list of objects this object is colliding with
	 * @return List of ICollider objects
	 */
	public List<ICollider> getColliders();
}
