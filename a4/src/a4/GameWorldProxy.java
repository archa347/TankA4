package a4;

import java.util.Iterator;

import a4.GameObjects.GameObject;
import a4.GameObjects.Movable.Projectile.Projectile;
import a4.GameObjects.Movable.Vehicle.Vehicle;

/**
 * Provides a proxy interface to GameWorld class.  This proxy only allows access to operations that do not change the state of the game world
 * @author Daniel Gallegos
 */
public class GameWorldProxy implements IGameWorld {
	
	private IGameWorld real;
	
	
	public GameWorldProxy(IGameWorld o) {
		this.real=o;
	}

	
	@Override
	public boolean getSound() {
		return real.getSound();
	}
	
	@Override
	public float getMapX() {
		return real.getMapX();
	}

	
	@Override
	public float getMapY() {
		return real.getMapY();
	}

	@Override
	public int getClock() {
		return real.getClock();
	}
	
	public int getTickRate() {
		return real.getTickRate();
	}

	
	@Override
	public int getLives() {
		return real.getLives();
	}

	
	@Override
	public int getScore() {
		return real.getScore();
	}
	
	@Override
	public Iterator<GameObject> iterator() {
		return new GameWorldProxyIterator();
		
	}
	
	@Override
	public int size() {
		return real.size();
	}
	
	@Override
	public Sound getSoundEffect(String string) {
		return real.getSoundEffect(string)	;
	}
	
	@Override
	public boolean isPaused() {
		return real.isPaused();
	}

	@Override
	public GameObject getPlayer() {
		return real.getPlayer();
	}
	
	/**
	 * Proxy iterator that overrides the remove() method
	 *
	 */
	private class GameWorldProxyIterator implements Iterator<GameObject> {
		private Iterator<GameObject> realItr;
		
		
		public GameWorldProxyIterator() {
			realItr = real.iterator();
		}

		/**
		 * Returns true if there is an object in the list
		 */
		@Override
		public boolean hasNext() {
			return realItr.hasNext();
		}
		
		/**
		 * Returns the next object in the list
		 */
		@Override
		public GameObject next() {
			return realItr.next();
		}

		
		/**
		 * Removes the last object returned by next
		 */
		@Override
		public void remove(){
			throw new UnsupportedOperationException("Operation not supported");
		}
	}


	
	//Remaining functions from IGameWorld are implemented with empty message bodies.  None of these functions should be called via Proxy
	
	

	
	@Override
	public void tankHit(Vehicle v, int d) {
		// proxy cannot execute this function

	}

	
	@Override
	public void tick() {
		// Proxy cannot execute this function

	}




	
	@Override
	public void setClock(int c) {
		// Proxy cannot execute this function

	}

	
	@Override
	public void setLives(int l) {
		// Proxy cannot execute this function

	}

	
	@Override
	public void setScore(int s) {
		// Proxy cannot execute this function

	}

	
	@Override
	public void speedup() {
		// Proxy cannot execute this function

	}

	
	@Override
	public void slowdown() {
		// Proxy cannot execute this function

	}

	
	@Override
	public void turnLeft() {
		// Proxy cannot execute this function

	}

	
	@Override
	public void turnRight() {
		// Proxy cannot execute this function

	}

	
	@Override
	public void firePlayer() {
		// Proxy cannot execute this function

	}

	
	@Override
	public void addObserver(IObserver o) {
		// Proxy cannot execute this function

	}

	
	@Override
	public void notifyObservers() {
		// Proxy cannot execute this function

	}


	@Override
	public void setSound(boolean o) {
		//Proxy cannot execute this function
		
	}


	@Override
	public void add(GameObject o) {
		// Proxy cannot execute this function
	}


	


	@Override
	public void remove(GameObject o) {
		// Proxy cannot execute this function
		
	}


	@Override
	public void pause(boolean p) {
		// proxy cannot execute this function
		
	}


	@Override
	public void reverse() {
		// proxy cannot execute this function
		
	}


	@Override
	public void fireSpikedGrenade() {
		// proxy cannot execute this function
		
	}


	@Override
	public void firePlasmaWave() {
		// proxy cannot execute this function
		
	}


	


	
	
	

	
}
