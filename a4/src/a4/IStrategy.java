package a4;

/**
 * Interface for objects implementing strategies.  
 * @author Daniel Gallegos
 *
 */
public interface IStrategy {
	/**
	 * Begin application of the strategy.  Called on every tick
	 */
	public void apply();
}
