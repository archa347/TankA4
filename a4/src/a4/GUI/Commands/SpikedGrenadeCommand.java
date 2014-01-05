package a4.GUI.Commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**
 * Command for firing SpikedGrenade object
 * @author Daniel Gallegos
 *
 */
public class SpikedGrenadeCommand extends AbstractAction {
	private GameWorld target;
	
	public SpikedGrenadeCommand(String n, GameWorld t) {
		super(n);
		target = t;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("\"SpikedGrenade\" command invoked");
		target.fireSpikedGrenade();
	}

}
