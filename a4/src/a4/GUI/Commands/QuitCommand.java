package a4.GUI.Commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 * Object referenced by objects implementing the Quit command
 * @author Daniel Gallegos
 *
 */
public class QuitCommand extends AbstractAction {
	

	/**
	 * Initiates command object. 
	 * @param n String name of the command
	 */
	public QuitCommand(String n) {
		super(n);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Quit") { 
			System.out.println("\"Quit\" command invoked!");
			int quit = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?");
			if (quit == 0) { System.out.println("Quitting"); System.exit(0);}
		}
	}

}
