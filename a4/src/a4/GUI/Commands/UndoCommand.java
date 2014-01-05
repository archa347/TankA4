package a4.GUI.Commands;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 * Command object for objects implementing the "Undo" command
 * @author Daniel Gallegos
 *
 */
public class UndoCommand extends AbstractAction {
	
	/**
	 * Initiates command object
	 * @param n String name of object
	 */
	public UndoCommand(String n) {
		super(n);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//More for this command later
		System.out.println("\"Undo\" command invoked!");
	}

}
