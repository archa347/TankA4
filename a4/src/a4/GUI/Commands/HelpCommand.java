package a4.GUI.Commands;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 * Command object for objects implementing the Help command
 * @author Daniel Gallegos
 *
 */
public class HelpCommand extends AbstractAction {
	
	/**
	 * Initializes HelpCommand object with specified name
	 * @param n Name for command
	 */
	public HelpCommand(String n) {
		super(n);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JOptionPane.showMessageDialog(null,
		"***********************GAMEPLAY***************************\n" +
		"Try and defeat the enemy tanks.  Defeat as many as possible\n" +
		"in the shortest time to win!\n" +
		"**********************************************************\n" +
		"***********************CONTROLS***************************\n"+
	   "           Tank Controls                                               \n" +
	   " Right Arrow | D : Turn your tank clockwise\n" +
	   " Left Arrow | A : Turn your tank counter-clockwise \n" +
	   " Up Arrow | W : increase your tank's speed\n" +
	   " Down Arrow | S : decrease your tank's speed\n" +
	   " Space | Enter : Fire a missile from your tank\n" +
	   " G : Spiked Grenade which attaches and damages tanks over time\n" +
	   " P : Plasma Wave that destroys any tank instantly\n" 

				
				);

	}

}
