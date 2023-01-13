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
			
			//fadeIn();
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
			fadeOut();	
			game = new Game(w);
			game.start();
					
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
		
		public void fadeIn() {
		    for (int i = 0; i <= 255; i += 5) {
		        gr.setColor(new Color(0, 0, 0, 255-i));
		        gr.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
		        w.repaint();
		        try {
		            Thread.sleep(25);
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
		    }
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
			gr.drawImage(img, 0, 0, Window.WIDTH, Window.HEIGHT, null);
			//graphics.drawImage(img, x, 0, window.WIDTH, window.HEIGHT, null);
			gr.setColor(Color.DARK_GRAY);
			gr.drawString("YOU ARE A PILOT IN THE 365 ", 65 , 155);
			gr.drawString("SAGITARIUS DEFENSE FLEET", 65 , 255);
			gr.drawString("THE MOTHERSHIP HAS BEEN", 65 , 355);
			gr.drawString("SERIOUSLY DAMAGED AND YOU", 65 , 455);
			gr.drawString("BEEN REQUESTED TO PROTECT IT", 65 , 555);
			gr.drawString("WHILE THE ENGINEERS FIX IT", 65, 655);

			gr.setColor(Color.YELLOW);
			
			gr.drawString("YOU ARE A PILOT IN THE 365 ", 70 , 150);
			gr.drawString("SAGITARIUS DEFENSE FLEET", 70, 250);
			gr.drawString("THE MOTHERSHIP HAS BEEN", 70, 350);
			gr.drawString("SERIOUSLY DAMAGED AND YOU", 70, 450);
			gr.drawString("BEEN REQUESTED TO PROTECT IT", 70 , 550);
			gr.drawString("WHILE THE ENGINEERS FIX IT", 70, 650);
			

			
		}
		
		public void initImages() {
			
			try {

				storyScreen.img = ImageIO.read(new File("res/BackgroundBlurr.png"));
				
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
		
//		public void playMusic(int i) throws LineUnavailableException {
//			
//			sound.setFile(i);
//			sound.play();
//			sound.loop();
//		}
//		
//		public void stopMusic() {
//			sound.stop();
//		}

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
					active = false;
					w.removeKeyListener(this);
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


