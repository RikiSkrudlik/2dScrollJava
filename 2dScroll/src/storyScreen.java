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

public class storyScreen extends Thread implements KeyListener{
		
		Window w;
		Graphics gr;
		static Image img;
		static Game game;
		static boolean active = true;
		static Sound sound;
		int counter = 0;

		public storyScreen(Window w) {

			this.w = w;
			this.gr = w.gr;
			w.addKeyListener(this);
			// TODO Auto-generated constructor stub
		} //Game over class it will be similar to the menu
		
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
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				repaintScreen();
				w.repaint();
				
				
			}
			//stopMusic();
			game = new Game(w);
			game.start();
			w.removeKeyListener(this);
			
					
		}
		
		void initSounds() throws LineUnavailableException {
			
//			sound = new Sound();
//			try {
//				//playMusic();
//			} catch (LineUnavailableException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}

		}
		
		public void repaintScreen() { //Paint Menu elements
			
			gr.setColor(Color.BLACK);
			gr.drawImage(img, 0, 0, w.WIDTH, w.HEIGHT, null);
			//graphics.drawImage(img, x, 0, window.WIDTH, window.HEIGHT, null);
			gr.setColor(Color.DARK_GRAY);
			gr.drawString("YOU ARE A PILOT IN THE 365 SAGITARIUS ", 120 , 255);
			gr.drawString("DEFENSE FLEET, THE MOTHERSHIP HAS", 120 , 355);
			gr.drawString("BEEN SERIOUSLY DAMAGED AND YOU HAVE", 120 , 455);
			gr.drawString("BEEN REQUESTED TO PROTECT IT WHILE", 120 , 555);
			gr.drawString("     THE ENGINEERS FIX IT ", 120 , 655);

			gr.setColor(Color.YELLOW);
			gr.drawString("YOU LOST... ", 120 , 350);
			gr.drawString("PLAY AGAIN (1, 2, 3)", 120 , 450);
			gr.drawString("TO SEE RECORDS PRESS 9", 120 , 550);


			
		}
		
		public void initImages() {
			
			try {

				gameOver.img = ImageIO.read(new File("res/BackgroundBlurr.png"));
				
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
		
		public void playMusic(int i) throws LineUnavailableException {
			
			sound.setFile(i);
			sound.play();
			sound.loop();
		}
		
		public void stopMusic() {
			sound.stop();
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_P) {
				try {
					counter += 1;
					game = new Game(w);
					active = false;
					w.removeKeyListener(this);
					game.start();
				} 
				catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_P) {
				active = false;
			}
		}
		

}


