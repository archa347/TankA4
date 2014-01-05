package a4;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;


/**
 * Encapsulating object for playing sound files
 * @author Daniel Gallegos
 *
 */
public class Sound {
	private AudioClip clip;
	
	public Sound(String fileName) {
		File file = new File(fileName);
		try {
			if (file.exists())	{
				clip = Applet.newAudioClip(file.toURI().toURL());
			}
			else throw new RuntimeException("Sound file not found: " + fileName);
	
		} catch (MalformedURLException e) {
			throw new RuntimeException("Malformed URL for sound file:" + e);
		}
	}
	
	/**
	 * Plays sound file if sound is enabled
	 * @param b Boolean true if sound is enabled
	 */
	public void play(boolean b) {
		if (b) clip.play();
	}
	
	/**
	 * Begins looping sound file if enabled
	 * @param b Boolean true if sound is enabled
	 */
	public void loop(boolean b) {
		if (b) clip.loop();
	}
	
	/**
	 * Stops playing looping sound file
	 */
	public void stop() {
		clip.stop();
	}
}
