package a4;

public interface IObserver {
	
	/**
	 * Notify this object of any changes to the observed object
	 * @param obj The observed object
	 * @param arg Any optional parameter
	 */
	public void update(IObservable obj, Object arg);

}
