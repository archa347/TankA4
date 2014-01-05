package a4.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;


/**
 * Special Pause JMenuItem that changes text when other componenents perform an action
 * @author Daniel Gallegos
 *
 */
public class PauseMenuItem extends JMenuItem implements ActionListener {

	public PauseMenuItem(String name) {
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
