package a4.GameObjects.Movable.Vehicle.Strategies;

import java.util.Random;

import a4.IStrategy;
import a4.GameObjects.Movable.Vehicle.Vehicle;

/**
 * Simple strategy that fires on every clock tick
 * @author Daniel Gallegos
 *
 */
public class LiberalStrategy implements IStrategy {
	private Vehicle client;
	private Random rand;
	
	public LiberalStrategy(Vehicle client) {
		this.client=client;
		rand = new Random();
	}
	
	@Override
	public void apply() {
		
		/*
		 * If client is blocked turn.  Random turns addresses issue where
		 * enemy tanks continuously collide because they turn at the same rate into eachother.
		 */
		
		if (client.isBlocked()) 
			if(rand.nextInt(3) > 0){
				client.turnRight();
				client.turnRight();
			}
			else {
				client.turnRight();
			}
		
		else {
			if (this.client.getWorld().getClock() % 1000 == 0) {  //fire every 1.5 seconds
				this.client.fire();
				int turn = rand.nextInt(30)+1;					//randomly turn 
				if (turn <= 10) client.turnLeft();
				if (turn > 20) client.turnRight();
			}
			if (client.getSpeed() < client.getAcceleration()*3) client.speedup();
		}
		
		
		
		
	}

}
