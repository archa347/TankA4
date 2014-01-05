package a4;
/**
 * Defines operations for a simple collection of objects
 * @author Daniel Gallegos
 *
 * @param <T> The type of the objects contained in the Collection
 */
public interface ICollection <T> {
	
	/**
	 * Adds an object to the collection
	 * @param o Object to be added
	 */
	public void add(T o);
	
	/**
	 * Returns the current size of the Collection
	 * @return Integer size of the collection
	 */
	public int size();
	
	/**
	 * Remove a specific object from the collection
	 * @param o Object to be removed
	 */
	public void remove(T o);

}
