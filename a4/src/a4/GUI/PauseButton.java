package a4.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Special Pause JButton that changes text when other components perform an Action
 * @author Daniel Gallegos
 *
 */
public class PauseButton extends JButton implements ActionListener {
	
	public PauseButton(String name) {
		super(name);
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand() == "Pause")
			this.setText("Play");
		if (arg0.getActionCommand() == "Play")
			this.setText("Pause");
	}

}
