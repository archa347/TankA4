package a4.GUI;

import java.util.Map;

import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GameMenu extends JMenuBar {
	
	private PauseMenuItem pauseItm;
	
	/**
	 * Sets up menu items, and adds commands as ActionListeners on commands
	 * @param commands A map of String command names to ActionListener command objects
	 */
	public GameMenu(Map<String,Action> commands) {
		
		//Creates File menu
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem newItm = new JMenuItem("New");
		fileMenu.add(newItm);
		newItm.setAction(commands.get("New"));
		
		JMenuItem saveItm = new JMenuItem("Save");
		fileMenu.add(saveItm);
		saveItm.setAction(commands.get("Save"));
		
		JMenuItem undoItm = new JMenuItem("Undo");
		fileMenu.add(undoItm);
		undoItm.setAction(commands.get("Undo"));
		
		JCheckBoxMenuItem soundItm = new JCheckBoxMenuItem("Sound");
		soundItm.setSelected(true);		
		fileMenu.add(soundItm);
		soundItm.setAction(commands.get("Sound"));

		JMenuItem aboutItm = new JMenuItem("About");
		fileMenu.add(aboutItm);
		aboutItm.setAction(commands.get("About"));
		
		JMenuItem quitItm = new JMenuItem("Quit");
		fileMenu.add(quitItm);
		quitItm.setAction( commands.get("Quit"));
		
		//Create Command menu and items
		JMenu commandMenu = new JMenu("Command");
		
		
		pauseItm = new PauseMenuItem("Pause");
		commandMenu.add(pauseItm);
		pauseItm.setAction(commands.get("Pause"));
		
		JMenuItem reverseItm = new JMenuItem("Reverse");
		commandMenu.add(reverseItm);
		reverseItm.setAction(commands.get("Reverse"));
		
		
		//Add menus to menu bar
		this.add(fileMenu);
		this.add(commandMenu);
	}
	
	public PauseMenuItem getPauseMenuItem() {
		return this.pauseItm;
	}

}
