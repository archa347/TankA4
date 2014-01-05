package a4.GUI.Commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
/**
 * Command class for objects implementing the About class
 * @author Daniel Gallegos
 *
 */
public class AboutCommand extends AbstractAction {
	
	/**
	 * Creates About command object
	 * @param n String name for control
	 */
	public AboutCommand(String n) {
		super(n);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("\"About\" command invoked!");
		JOptionPane.showMessageDialog(null, "Tankity Tank! \n" +
				"by Daniel Gallegos\n" +
				"v 1.2\n"); 
	}

}
