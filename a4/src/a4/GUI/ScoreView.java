package a4.GUI;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import a4.IGameWorld;
import a4.IObservable;
import a4.IObserver;


/**
 * Defines a view of the GameWorld to display game state information in the GUI
 * @author Dan
 *
 */
public class ScoreView extends JPanel implements IObserver {

	private JLabel time,score,lives,sound;
	
	
	/**
	 * Default constructor.  Arranges labels inside the panel with default values.
	 */
	public ScoreView() {
		
		this.setLayout(new GridLayout(0,6));
		this.setBorder(new LineBorder(Color.black, 2));
		time = new JLabel("TIME: 0");
		lives = new JLabel("LIVES: 0");
		score = new JLabel("SCORE: 0");
		sound = new JLabel("SOUND: ON");
		this.add(new JLabel());
		this.add(time);
		this.add(score);
		this.add(lives);
		this.add(sound);
		this.add(new JLabel());
		this.add(new JLabel());
		
	}
	
	
	
	/**
	 * Allows the ScoreView to be updated by the object which it is observing
	 */
	@Override
	public void update(IObservable obj, Object arg) {
		IGameWorld gw = (IGameWorld) obj;
		
		score.setText("SCORE: " + gw.getScore());
		time.setText("TIME: " + (gw.getClock() / 1000) ); //convert clock from milliseconds to seconds
		lives.setText("LIVES: " + gw.getLives());
		
		if (gw.getSound())
			sound.setText("SOUND: ON");
		else sound.setText("SOUND: OFF");
		
		System.out.println("Score View has been updated!");
	}

}
