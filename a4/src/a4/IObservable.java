package a4;


public interface IObservable {
	/**
	 * Registers an object as an observer to this object
	 * @param o  The observer object to be registered.  Must implement IObserver interface
	 */
	public void addObserver(IObserver o);
	
	/**
	 * Notifies observer objects of changes to the object, via their update() method
	 */
	public void notifyObservers();
}
