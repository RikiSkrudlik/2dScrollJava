import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

	Clip clip;
	URL soundURL[] = new URL[30];
	
	public Sound() { //get all the sounds in an URL array
		
			soundURL[0] = getClass().getResource("sound/Main.wav"); //Main song
			soundURL[1] = getClass().getResource("sound/Menu.wav"); //Menu song
			soundURL[2] = getClass().getResource("sound/gameOver.wav");
			//Now sound effects
			soundURL[3] = getClass().getResource("sound/playerBullet.wav");
			soundURL[4] = getClass().getResource("sound/enemyBullet.wav");
			soundURL[5] = getClass().getResource("sound/playerHit.wav");
			soundURL[6] = getClass().getResource("sound/extraLife.wav");
			soundURL[7] = getClass().getResource("sound/ulti.wav");
			soundURL[8] = getClass().getResource("sound/click.wav");

	}
	
	public void setFile(int i) throws LineUnavailableException {
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void play() {
		
		clip.start();
		
	}
	
	public void loop() {
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		
	}
	public void stop() {
		
		clip.stop();
		
	}
}