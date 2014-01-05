package a4;

import a4.GameObjects.GameObject;
import a4.GameObjects.Movable.Projectile.Projectile;
import a4.GameObjects.Movable.Vehicle.Vehicle;


/**
 * Defines the functions used in the GameWorld class for proxy implementation
 * @author Daniel Gallegos
 *
 */
public interface  IGameWorld extends IObservable, Iterable<GameObject>,ICollection<GameObject> {
	public float getMapX();
	public float getMapY();
	public int getClock();
	public int getTickRate();
	public int getLives();
	public int getScore();
	public GameObject getPlayer();
	public boolean getSound();
	public boolean isPaused();
	
	public void tankHit(Vehicle v, int d);
	public void tick();
	public void reverse();
	
	
	public void setClock(int c);
	public void setLives(int l);
	public void setScore(int s);
	public void setSound(boolean o);

	
	public void speedup();
	public void slowdown();
	public void turnLeft();
	public void turnRight();
	public void firePlayer();
	public void add(GameObject o);
	public Sound getSoundEffect(String string);
	public void pause(boolean paused);
	public void fireSpikedGrenade();
	public void firePlasmaWave();
}
