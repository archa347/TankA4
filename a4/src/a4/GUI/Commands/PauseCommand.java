package a4.GUI.Commands;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JMenuItem;

import a4.Game;
import a4.IGameWorld;
/**
 * Command to pause the gameWorld
 * @author Daniel Gallegos
 *
 */
public class PauseCommand extends AbstractAction {
	private Game target;
	private boolean paused;
	
	/**
	 * Constructor
	 * @param n String name of the command
	 * @param g Game object target
	 */
	public PauseCommand(String n,Game g) {
		super(n);
		this.target = g;
		this.paused = false;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		/*
		 * Pause the game.  Set text on JButton for Resume
		 */
		if (arg0.getActionCommand() == "Pause") { 
			target.pause(paused=true);
		}
		
		
		/*
		 * Resume the game.  Set text on the JButton for Pause
		 */
		else if(arg0.getActionCommand() == "Play") {
			target.pause(paused=false);
		}
		
	}
	

}
