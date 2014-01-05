package a4.GUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class CommandPanel extends JPanel {
	private JButton helpBtn, quitBtn, reverseBtn; 
	private PauseButton pauseBtn;
	
	/**
	 * Builds Button Panel for the main GUI
	 * @param commands A map of the commands to be referenced and added as action listeners
	 */
	public CommandPanel(Map<String, Action> commands) {
		this.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), "Commands"));
		this.setLayout(new GridLayout(20,1));
		
		helpBtn = new JButton("Help");
		helpBtn.setAction(commands.get("Help"));
		helpBtn.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		
		quitBtn = new JButton("Quit");
		quitBtn.setAction(commands.get("Quit"));
		quitBtn.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		
		reverseBtn = new JButton("Reverse");
		reverseBtn.setAction(commands.get("Reverse"));
		reverseBtn.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		
		pauseBtn = new PauseButton("Pause");
		pauseBtn.setAction(commands.get("Pause"));
		pauseBtn.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		
		commands.get("Reverse").setEnabled(false);
		
		this.add(pauseBtn);
		this.add(reverseBtn);
		this.add(helpBtn);
		this.add(quitBtn);
	}
	
	public PauseButton getPauseButton() {
		return this.pauseBtn;
	}
}
