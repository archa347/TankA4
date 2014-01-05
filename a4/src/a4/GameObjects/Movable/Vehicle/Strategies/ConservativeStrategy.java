package a4.GameObjects.Movable.Vehicle.Strategies;

import java.util.Random;

import a4.IStrategy;
import a4.GameObjects.Movable.Vehicle.Vehicle;

/**
 * A more conservative strategy that fires only every other turn
 * @author Daniel Gallegos
 *
 */
public class ConservativeStrategy implements IStrategy {
	private Vehicle client;

	private Random rand;
	
	public ConservativeStrategy(Vehicle client) {
		this.client = client;
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
				client.turnLeft();
				client.turnLeft();
			}
			else {
				client.turnLeft();
			}
		else {
			if (this.client.getWorld().getClock() % 3000 == 0) {  //fire every three seconds
				this.client.fire();
				int turn = rand.nextInt(20)+1;
				if (turn <= 5) client.turnLeft();				//randomly turn left, right or continue straight
				if (turn >= 15) client.turnRight();
			}
			if (client.getSpeed() < client.getAcceleration()) client.speedup();
			else if (client.getSpeed()  > client.getAcceleration()) client.slowdown();
		}
	}

}
