package a4.GUI.Commands;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
/**
 * Command object used by objects implementing the Save command
 * @author Daniel Gallegos
 *
 */
public class SaveCommand extends AbstractAction {
	/**
	 * Initiates command object
	 * @param n String name of command
	 */
	public SaveCommand(String n) {
		super(n);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Save")
			System.out.println("\"Save\" command invoked");
	}

}
