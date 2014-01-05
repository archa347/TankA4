package a4.GUI.Commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.IGameWorld;

/**
 * Command object for the Reverse command
 * @author Daniel Gallegos
 *
 */
public class ReverseCommand extends AbstractAction {
	
	private IGameWorld target;
	
	public ReverseCommand(String n, IGameWorld g) {
		super(n);
		target = g;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		target.reverse();
	}

}
