import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;

public class Menu extends Thread implements KeyListener{
	
	Window w;
	Graphics gr;
	
	static Image img;
	static boolean active = true;
	static Sound sound;
	
	int recordOrplay;
	Boolean isRecord = false;
	
	public Menu(Window w) {
		
		this.w = w;
		this.gr = w.gr;
		w.addKeyListener(this);
	}
	
	public void run() {//Menu loop, similar procedure to the game class
				
		initImages();
		initFont();
		try {
			initSounds();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
						
		while(active) {
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaintScreen();
			
		}
		
		if (recordOrplay == 0) { //Record has been clicked
			
			fadeOut();
			storyScreen storyScreen = new storyScreen(w);
			storyScreen.start();
		}
		else {
			
			displayRecords dr = new displayRecords(w);
			dr.start();
		}
		active = false;
				
	}
	
	public void initImages() {
		
		try {

			Menu.img = ImageIO.read(new File("res/BackgroundBlurr.png"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void initFont() {
		
		Font font;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("font/arcade.ttf"));
			font = font.deriveFont(Font.PLAIN, 26);
			gr.setFont(font);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	void initSounds() throws LineUnavailableException {
		
		sound = new Sound();

	}
	
	public void playSE(int i) throws LineUnavailableException {
		
		sound.setFile(i);
		sound.play();
	}
	
	public void repaintScreen() { //Paint Menu elements
		
		gr.setColor(Color.BLACK);
		gr.drawImage(img, 0, 0, Window.WIDTH, Window.HEIGHT, null);
		
		gr.setColor(Color.DARK_GRAY);
		
		gr.drawString("EASY MODE (PRESS 1) ", 115 , 455);
		gr.drawString("NORMAL MODE (PRESS 2) ", 115 , 555);
		gr.drawString("HARD MODE (PRESS 3) ", 115 , 655);
		gr.drawString("SEE RECORD TABLE (PRESS 9) ", 115 , 150);
		
		gr.setColor(Color.YELLOW);
		
		gr.drawString("EASY MODE (PRESS 1) ", 120 , 450);
		gr.drawString("NORMAL MODE (PRESS 2) ", 120 , 550);
		gr.drawString("HARD MODE (PRESS 3) ", 120 , 650);
		gr.drawString("SEE RECORD TABLE (PRESS 9) ", 115 , 155);
		
		w.repaint();

		
	}
	
	public void fadeOut() {
		
	    for (int i = 0; i <= 255; i += 5) {
	    	
	        gr.setColor(new Color(0, 0, 0, i));
	        gr.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
	        w.repaint();
	        try {
	        	
	            Thread.sleep(25);
	        } catch (InterruptedException e) {
	        	
	            e.printStackTrace();
	        }
	    }
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_1) {
			try {

				playSE(8);
				active = false;
				w.removeKeyListener(this);
				Game.gamemode = 1; //Easy mode
			} 
			catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			}
		else if (key == KeyEvent.VK_2) { 
			try {
				
				playSE(8);
				active = false;
				w.removeKeyListener(this);
				Game.gamemode = 2; //Medium	

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
		}
		else if (key == KeyEvent.VK_3) { 
			try {
				
				playSE(8);
				Game.gamemode = 3; //Hard mode
				active = false;
				w.removeKeyListener(this);


			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (key == KeyEvent.VK_9) { //Show records

				recordOrplay = 1;
				active = false;
				w.removeKeyListener(this);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	

}