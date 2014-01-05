package a4.GUI.Commands;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;

import a4.GameWorld;


public class SoundCommand extends AbstractAction {
	private GameWorld target;
	
	/**
	 * Initiates command object
	 * @param n String name of command
	 * @param target Gameworld target of command
	 */
	public SoundCommand(String n,GameWorld target) {
		super(n);
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//If sound is on, disable.  If off, enable
		if (e.getActionCommand() == "Sound")
			if (target.getSound())
				target.setSound(false);
			else target.setSound(true);
		System.out.println("Sound Menu Item Invoked");
	}

}
