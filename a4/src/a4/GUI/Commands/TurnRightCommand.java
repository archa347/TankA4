package a4.GUI.Commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**
 * Command object for objects implementing the Turn Right command;
 * @author Daniel Gallegos
 *
 */
public class TurnRightCommand extends AbstractAction {

	private GameWorld target;
	
	/**
	 * Initializes command object
	 * @param n String name of command
	 * @param t GameWorld target of command
	 */
	public TurnRightCommand(String n, GameWorld t) {
		super(n);
		target=t;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("\"Turn Right\" command invoked");
		target.turnRight();

	}

}
