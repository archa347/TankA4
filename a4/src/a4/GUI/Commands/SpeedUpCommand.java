package a4.GUI.Commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**
 * Command object for objects implementing the Speed Up command
 * @author Dan
 *
 */
public class SpeedUpCommand extends AbstractAction {
	private GameWorld target;
	
	public SpeedUpCommand(String n, GameWorld t) {
		super(n);
		target=t;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("\"Speed Up\" command invoked");
		target.speedup();
	}

}
