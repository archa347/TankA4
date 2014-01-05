package a4.GUI.Commands;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**
 * Command object for objects implementing the Tick command			
 * @author Dan
 *
 */
public class TickCommand extends AbstractAction {

	private GameWorld target;
	
	/**
	 * Creates TickCommand with target
	 * @param n Name for command button
	 * @param t GameWorld target of command
	 */
	public TickCommand(String n, GameWorld t) {
		super(n);
		target = t;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Tick") {
			target.tick();
		}

	}

}
