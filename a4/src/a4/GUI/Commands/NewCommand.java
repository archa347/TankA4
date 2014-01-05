package a4.GUI.Commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
/**
 * Command object for objects implementing the New command
 * @author Dan
 *
 */
public class NewCommand extends AbstractAction {
	/**
	 * Initiates command object.  
	 * @param n String name of the command
	 */
	public NewCommand(String n) {
		super(n); 
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "New")
			System.out.println("\"New\" command invoked!");
	}

}
