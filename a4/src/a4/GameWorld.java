package a4;

import java.io.File;
import java.util.*;

import javax.swing.JOptionPane;

import a4.GUI.ICollider;
import a4.GUI.ISelectable;
import a4.GameObjects.*;
import a4.GameObjects.Landscape.*;
import a4.GameObjects.Movable.Projectile.*;
import a4.GameObjects.Movable.Vehicle.*;


public class GameWorld implements IGameWorld{
	private List<GameObject> worldObjects;
	private float mapX;
	private float mapY;
	private static Random generator = new Random();
	private Vehicle player;
	private int score;
	private int clock;
	private int lives;
	private boolean sound;
	private List<IObserver> observers;
	private final int tickRate;
	private HashMap<GameObject,Boolean> seen;
	private HashMap<String, Sound> sounds;
	private boolean paused;
	
	/**
	 * 
	 * @param x X parameter of map size
	 * @param y Y parameter of map size
	 * @param l Player lives
	 * @param t enemy tanks
	 * @param r Number of rocks
	 * @param tr Number of trees
	 */
	public GameWorld(float x, float y, int l, int t, int r, int tr) {
		this.mapX = x;
		this.mapY = y;
		this.score = 0;
		this.lives = 3;
		this.clock = 0;
		this.sound=true;
		paused = false;
		this.tickRate = 20;
		
		this.worldObjects = new Vector<GameObject>();
		this.observers = new Vector<IObserver>();
		this.seen = new HashMap<GameObject,Boolean>();
		
		this.add(this.player = new Tank(this.mapX/2, this.mapY/ 2, generator.nextInt(360),true));
	
		
		for (int i=0; i < t; i++) {
			this.add(new Tank(this.mapX * generator.nextFloat(), this.mapY * generator.nextFloat(),  generator.nextInt(360),false));
		}
		
		for(int i = 0; i < r; i++) {
			this.add(new Rock(this.mapX * generator.nextFloat(), this.mapY * generator.nextFloat(), generator.nextInt(50)+15, generator.nextInt(50)+15));
		}
		
		for(int i=0; i < tr; i++) {
			this.add(new Tree(this.mapX * generator.nextFloat(), this.mapY * generator.nextFloat(), generator.nextInt(50)+15));
		}
		
		
		//Add sounds
		
		sounds = new HashMap<String,Sound>();
		String slash = File.separator;
		String soundPath = "." + slash + "a4"+ slash + "sounds" + slash;
		sounds.put("fire", new Sound(soundPath + "fire.wav"));
		sounds.put("loop", new Sound(soundPath + "loop.wav"));
		sounds.put("explosion", new Sound(soundPath + "explosion.wav"));
		sounds.put("crash", new Sound(soundPath + "crash.wav"));
	
	}
	
	/**
	 * Returns map size in the X axis
	 * @return Map size in the X axis as float
	 */
	public float getMapX() {
		return this.mapX;
	}
	
	/**
	 * Returns map size in the Y axis
	 * @return Map in the Y axis as float
	 */
	public float getMapY() {
		return this.mapY;
	}
	
	/**
	 * Returns the current number of lives the player has left
	 * @return Current lives as integer
	 */
	public int getLives() {
		return this.lives;
	}
	
	public int getTickRate() {
		return tickRate;
	}

	/**
	 * Sets the current lives 
	 * @param l New count of lives as integer
	 */
	public void setLives(int l) {
		this.lives = l;
	}
	
	/**
	 * Gets the current score
	 * @return Current score as integer
	 */
	public int getScore() {
		return this.score;
	}
	
	/**
	 * Sets the current score
	 * @param s New score as integer
	 */
	public void setScore(int s) {
		this.score = s;
	}
	
	/**
	 * Gets the current time
	 * @return The current time
	 */
	public int getClock() {
		return this.clock;
	}
	
	/**
	 * Sets the current time
	 * @param cl New time as integer
	 */
	public void setClock(int cl) {
		this.clock = cl;
	}
	

	@Override
	public boolean isPaused() {
		return this.paused;
	}

	
	
	/**
	 * Turns player tank to the right
	 */
	public void turnRight() {
		this.player.turnRight();
		this.notifyObservers();
	}
	
	/**
	 * Turns player tank to the left
	 */
	public void turnLeft() {
		this.player.turnLeft();
		this.notifyObservers();
	}
	
	/**
	 * Increases player tank speed
	 */
	public void speedup(){
		this.player.speedup();
		this.notifyObservers();
	}
	
	/**
	 * Decreases player tank speed
	 */
	public void slowdown() {
		this.player.slowdown();
		this.notifyObservers();
	}
	
	/**
	 * Fires a missile from the player tank, adds missile to game world
	 */
	public void firePlayer() {
		this.player.fire();
		this.notifyObservers();
	}
	
	/**
	 * Fires a spiked grenade from a player tank
	 */
	public void fireSpikedGrenade() {
		player.spikedGrenade();
		this.notifyObservers();
	}
	
	
	
	/**
	 * Hits a vehicle with a projectile. Calls hit() on vehicle
	 * @param hit	Vehicle that is hit
	 */
	public void tankHit(Vehicle hit, int d) {
		//if vehicle is not player, hit tank, increase score by 20, remove tank if it is destroyed
		if(hit != this.player) {
			if(hit.hit(d)) this.score+=20;	//if tank survives hit, only increase score
			else {
					this.score+=20; 			//if destroyed, increase score
					hit.setDelete(true);	//remove tank
				}
		}
		//otherwise, hit player
		else {
			if(!this.player.hit(d)) 	//if hit() returns false (player destroyed)		
				player.setDelete(true);
		}
		
	}
	
	
	
	
	
	/**
	 * Advances the game world one time unit. Calls update() method in all objects
	 */
	public void tick() {
		this.clock += tickRate;
		Iterator<GameObject> itr = this.iterator();
		
		while (itr.hasNext()) {
			GameObject o = itr.next();  
			o.update(tickRate);		//update all objects time dependent properties
			if (o instanceof Projectile)
				if (((Projectile) o).getLifetime() < 0) o.setDelete(true); //Delete missiles at end of tick
		}
		
		this.collisionCheck();
		
		int tanksRemoved=0;
		boolean newPlayer = false;
		itr = this.iterator();			
		while (itr.hasNext()) {
			GameObject n = itr.next();		//check for objects to be removed
			if (n.isDelete()) {
				itr.remove();
				if (n instanceof Vehicle)	
					if (((Vehicle) n).isPlayer()) {
						player=loseLife();	//if object is player, lose a life
						newPlayer = true;
					}
					else tanksRemoved++;	//if not player, will need a new tank
			}
		}
		
		//Add new tanks to replace destroyed tanks
		for (;tanksRemoved > 0; tanksRemoved--) {
			this.add(new Tank(this.mapX * generator.nextFloat(), this.mapY * generator.nextFloat(), generator.nextInt() * 5 % 359,false));
		}
		
		//Game over if no more lives
		if (lives < 0) {
			JOptionPane.showMessageDialog(null, "GAME OVER!");
			System.exit(0);
		}
		
		//Replace player if there are lives
		if (newPlayer) {
			this.add(player);
		}
		
		this.notifyObservers();
	}
	
	
	/**
	 * Function for when player's tank has been destroyed.  Loses one life, if out of lives game is over.
	 * Otherwise, spawns new tank for player.
	 */
	private Vehicle loseLife() {
		this.lives--;
		this.notifyObservers(); 
		JOptionPane.showMessageDialog(null, "Your tank has been destroyed!");
		
		if (this.lives < 0) { return null;}
		
		player.setDelete(true);
		
		JOptionPane.showMessageDialog(null, "You have " + this.lives + " lives left.");
		System.out.println("Respawning");
		
		return new Tank(this.mapX * generator.nextFloat(), this.mapY * generator.nextFloat(), generator.nextInt() * 5 % 359,true);
	}

	/**
	 * Adds an observer to the observer list
	 */
	@Override
	public void addObserver(IObserver o) {
		this.observers.add(o);
	}
	
	/**
	 * Notifies all observers when the game state changes
	 */
	@Override
	public void notifyObservers() {
		Iterator<IObserver> itr = observers.iterator();
		
		while (itr.hasNext()) {
			itr.next().update(new GameWorldProxy(this),null);
		}
	}
	
	
	/**
	 * Adds an object to the game world
	 * @param o Object to be added to the gameworld
	 */
	public void add(GameObject o) {
		worldObjects.add(o);
		o.setWorld(this);
	}
	
	/**
	 * Returns current state of sound
	 */
	public boolean getSound() {
		return this.sound;
	}
	
	/**
	 * Turns sound on or off
	 * @param on Boolean true for on, false for off
	 */
	public void setSound(boolean on) {
		this.sound = on;
		if (this.sound == false) sounds.get("loop").stop();	//if sound is off, stop playing sound
		
		if (!this.paused) sounds.get("loop").loop(this.sound);				//restart sound if sound is on
		this.notifyObservers();
	}
	
	/**
	 * Returns an iterator to the worldObjects collection
	 */
	@Override
	public Iterator<GameObject> iterator() {
		return new GameWorldIterator();
	}
	
	@Override
	public int size() {
		return worldObjects.size();
	}

	@Override
	public void remove(GameObject o) {
		worldObjects.remove(o);
	}
	
	
	/**
	 * Checks objects in the world to determine if any collisions have occurred
	 */
	private void collisionCheck() {
		
		Iterator<GameObject> itr = this.iterator();
		
		while (itr.hasNext()) {
			GameObject curObj = itr.next();
			
			//Check current object against all objects in World  
			Iterator<GameObject> itr1 = this.iterator();
			while (itr1.hasNext()) {
				GameObject nextObj = itr1.next();
				if(curObj.collidesWith(nextObj)) { //If object collides, add to colliders list
					curObj.addCollider(nextObj);
				}
			}
		}
		
		/*Go through every object and handle collision for each object colliding with
		 */
		itr = this.iterator();
		while (itr.hasNext()) {
			GameObject curObj = itr.next();
			Iterator<ICollider> itr1= curObj.getColliders().iterator();
			while (itr1.hasNext()) {
				curObj.handleCollision(itr1.next());
			}
			curObj.resetColliders();		//reset this objects colliders list after finished
		}
	}
	
	/**
	 * Returns the named sound
	 * @param soundName String name of the sound
	 * @return the Sound object
	 */
	public Sound getSoundEffect(String soundName) {
		return sounds.get(soundName);
	}
	
	public void startBackground() {
		sounds.get("loop").loop(this.sound);
	}

	/**
	 * Pauses the gameWorld, stopping sound
	 * @param p
	 */
	@Override
	public void pause(boolean p) {
		this.paused = p;
		if (paused) {
			this.sounds.get("loop").stop();  //if paused, stop loop
		}
		else this.sounds.get("loop").loop(sound); //if not paused, loop starts (depending on current sound setting)
	}
	
	
	/**
	 * Reverses the direction of selected tanks
	 */
	@Override
	public void reverse() {
		for (GameObject o : this) {
			if (o instanceof ISelectable) {
				if (((ISelectable) o).isSelected())
					((Tank) o).setDirection(((Tank) o).getDirection()+180);
			}
				
		}
		this.notifyObservers();
	}


	
	
	
	/**
	 * An iterator for the GameObjects contained in the GameWorld
	 * @author Dan
	 *
	 */
	private class GameWorldIterator implements Iterator<GameObject> {
		
		private int index;
		private GameObject next;
		
		
		public GameWorldIterator() {
			index = -1;
			next = null;
		}

		/**
		 * Returns true if there is an object in the list
		 */
		@Override
		public boolean hasNext() {
			if (worldObjects.size() <= 0)
				return false;
			if (index >= worldObjects.size()-1)
				return false;
			else return true;
		}
		
		/**
		 * Returns the next object in the list
		 */
		@Override
		public GameObject next() {
			if (this.hasNext()) {
				index++;
				return next = worldObjects.get(index);
			}
			else throw new NoSuchElementException("No next item in the iteration");
		}

		
		/**
		 * Removes the last object returned by next
		 */
		@Override
		public void remove(){
			if (next == null) throw new IllegalStateException("Attempted remove() without invoking next()");
			else  {
				worldObjects.remove(next);
				next = null;
				index--;  //array list shifts the remaining elements of the list to the left
			}
		}
		
	}
	
	@Override
	public void firePlasmaWave() {
		player.plasmaWave();
		this.notifyObservers();
	}

	@Override
	public GameObject getPlayer() {
		return this.player;
	}





}
