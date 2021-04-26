package resources;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {

	// play clip
	private Clip clip;

	public Audio(String sound) {

		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource(sound));
			clip = AudioSystem.getClip();
			clip.open(audio);
		} catch (Exception e) {
		}
	}

	//getters and setters
	public Clip getClip() {
		return clip;
	}

	// play the clip
	public void play() {
		clip.start();
	}

	//stop the clip
	public void stop() {
		clip.stop();
	}

	//play the sound
	public static void playSound(String son) {
		Audio s = new Audio(son);
		s.play();
	}
}