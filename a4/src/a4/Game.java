package a4;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.*;

import a4.GUI.*;
import a4.GUI.Commands.*;

public class Game extends JFrame {
	private GameWorld gw;
	private Scanner in;
	private Map<String,Action> commands;
	private Timer timer;
	/**
	 * Initiates Game.  Prompts user for game options from command line, then creates game world.
	 * Upon finishing a game, prompts user to restart or quit.
	 */
	
	public Game() {
		in = new Scanner(System.in);
		int tanks=5;
		int rocks=10;
		int trees=10;
		
		/*
			//Prompt player for the number of enemy tanks
		while(true) {
			try {
				System.out.print("How many enemy tanks?: ");
				tanks = in.nextInt();
				in.nextLine();
				if (tanks < 1){ System.out.println("Please enter a positive integer"); continue;}
				break;
			} catch (RuntimeException e) {
				in.nextLine();
				System.out.println("Please enter a positive integer");
			}
		 }	
		
		//Prompt player for the number of rocks
		while(true) {
			try {
				System.out.print("How many rocks?: ");
				rocks = in.nextInt();
				if (rocks < 0){ System.out.println("Please enter a positive integer"); continue;}
				break;
			}  catch (RuntimeException e) {
				in.nextLine();
				System.out.println("Please enter a positive integer");
			}
		}
		
		//prompt player for the number of trees
		while(true) {
			System.out.print("How many trees: ");
			try {
				trees = in.nextInt();
				in.nextLine();
				if (trees < 0){ System.out.println("Please enter a positive integer"); continue;}
				break;
			} catch (RuntimeException e) {
				in.nextLine();
				System.out.println("Please enter a positive integer");
			} 
		}*/

		//Define a new GameWorld, size 1024x1024, 3 lives for the player, with the specified number of other game objects
		this.gw = new GameWorld(2000,2000,3,tanks,rocks,trees);
		
		buildCommands();
		
		this.timer = new Timer(20, this.commands.get("Tick"));
		timer.setActionCommand("Tick");
	
		setSize(1000, 800);
		setTitle("Tankity Tank!");
		setLayout(new BorderLayout());
		
		CommandPanel commandPanel = new CommandPanel(commands);
		this.add(commandPanel, BorderLayout.WEST);
		
		GameMenu bar = new GameMenu(commands);
		this.setJMenuBar(bar);
		
		
		commandPanel.getPauseButton().addActionListener(bar.getPauseMenuItem());
		bar.getPauseMenuItem().addActionListener(commandPanel.getPauseButton());
		
		
		//Create score view and add as observer to game world
		ScoreView statusPanel = new ScoreView();
		this.add(statusPanel,BorderLayout.NORTH);
		
		//Create Map view and add as observer to game world
		MapView mapPanel = new MapView(commands,gw);
		this.add(mapPanel, BorderLayout.CENTER);
		
		
		gw.addObserver(statusPanel);
		gw.addObserver(mapPanel);
		
		
		
		setVisible(true);
		
		gw.notifyObservers();
		timer.start();
		gw.startBackground();
	}
		
	/**
	 * Creates command objects used by the game
	 */
	private void buildCommands() {
		commands = new HashMap<String,Action>();
		commands.put("Quit", new QuitCommand("Quit"));
		commands.put("New", new NewCommand("New"));
		commands.put("Save",new SaveCommand("Save"));
		commands.put("Undo",new UndoCommand("Undo"));
		commands.put("Sound",new SoundCommand("Sound",gw));
		commands.put("About",new AboutCommand("About"));
		commands.put("Help",new HelpCommand("Help"));
		commands.put("Tick",new TickCommand("Tick",gw));
		commands.put("r",new TurnRightCommand("Turn Right",gw));
		commands.put("l",new TurnLeftCommand("Turn Left",gw));
		commands.put("i", new SpeedUpCommand("Speed Up",gw));
		commands.put("k", new SlowDownCommand("Slow Down",gw));
		commands.put("f", new FireCommand("Fire",gw));
		commands.put("Pause", new PauseCommand("Pause",this));
		commands.put("Reverse", new ReverseCommand("Reverse",gw));
		commands.put("g", new SpikedGrenadeCommand("Spiked Grenade",gw));
		commands.put("p", new PlasmaWaveCommand("Plasma Wave", gw));
	}
	
	/**
	 * Pauses the game and enables/disables commands based on game state
	 * @param paused Boolean true indicating game should be paused
	 */
	public void pause(boolean paused) {
		if (paused == true) {
			this.timer.stop();
			commands.get("r").setEnabled(false);
			commands.get("l").setEnabled(false);
			commands.get("i").setEnabled(false);
			commands.get("k").setEnabled(false);
			commands.get("f").setEnabled(false);
			commands.get("g").setEnabled(false);
			commands.get("p").setEnabled(false);
			commands.get("Reverse").setEnabled(true);
			
			gw.pause(paused);
		}
		else {
			gw.pause(paused);
			
			commands.get("r").setEnabled(true);
			commands.get("l").setEnabled(true);
			commands.get("i").setEnabled(true);
			commands.get("k").setEnabled(true);
			commands.get("f").setEnabled(true);
			commands.get("g").setEnabled(true);
			commands.get("p").setEnabled(true);
			commands.get("Reverse").setEnabled(false);
			
			this.timer.start();
			
		}
	}
}
 