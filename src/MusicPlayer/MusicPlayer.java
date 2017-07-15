package MusicPlayer;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicPlayer {

	private AudioInputStream audioIn;
	private Clip clip;

	public MusicPlayer() {
		try {
			audioIn = AudioSystem.getAudioInputStream(new File("resources\\audios\\main\\Ramin Djawadi - Main Title Game Of Thrones.wav"));

			clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
    }

}
