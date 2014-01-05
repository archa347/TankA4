package a4.GUI.Commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**
 * Command object for the Plasma Wave command
 * @author Dan
 *
 */
public class PlasmaWaveCommand extends AbstractAction {
	private GameWorld target;
	
	public PlasmaWaveCommand(String n, GameWorld t) {
		super(n);
		this.target = t;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.target.firePlasmaWave();

	}

}
