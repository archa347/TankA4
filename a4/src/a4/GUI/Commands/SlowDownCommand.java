package a4.GUI.Commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**
 * Command object for objects implementing the Slow Down command
 * @author Dan
 *
 */
public class SlowDownCommand extends AbstractAction {
	private GameWorld target;
	
	public SlowDownCommand(String n, GameWorld t) {
		super(n);
		target=t;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("\"Slow Down\" command invoked");
		target.slowdown();
	}

}