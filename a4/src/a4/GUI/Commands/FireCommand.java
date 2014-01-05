package a4.GUI.Commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**
 * Command object for objects implementing the Fire command
 * @author Dan
 *
 */
public class FireCommand extends AbstractAction {
	private GameWorld target;
	
	/**
	 * Initializes command object with target
	 * @param n String name of command
	 * @param t Gameworld target of command
	 */
	public FireCommand(String n, GameWorld t) {
		super(n);
		target=t;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Fire" || e.getActionCommand().toString().equals(" ")) {
			System.out.println("\"Fire\" command invoked");
			target.firePlayer();
		}

	}

}