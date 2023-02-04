import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;

public class gameOver extends Thread implements KeyListener{
	
	Window w;
	Graphics gr;
	recordTable table;
	String name;
	long points;
	
	static Image img;
	static Game game;
	static boolean active = true;
	static Sound sound;

	public gameOver(Window w, String name, long points) {

		this.w = w;
		this.gr = w.gr;
		this.name = name;
		this.points = points;
		w.addKeyListener(this);
		// TODO Auto-generated constructor stub
	} //Game over class it will be similar to the menu
	
	public void run() {//Menu loop, similar procedure to the game class
		
		table = new recordTable (name, points); //First we update the recordTable
		try {
			table.updateTable();
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
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
		}
		
		stopMusic();
		fadeOut();
		game = new Game(w, name);
		game.start();
		w.removeKeyListener(this);		
				
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
	
	void initSounds() throws LineUnavailableException {
		
		sound = new Sound();
		
		try {
			playMusic(2);
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	public void repaintScreen() { //Paint Menu elements
		
		gr.setColor(Color.BLACK);
		gr.drawImage(img, 0, 0, Window.WIDTH, Window.HEIGHT, null);
		
		gr.setColor(Color.DARK_GRAY);
		gr.drawString("YOU LOST... ", 115 , 355);
		gr.drawString("PLAY AGAIN (1, 2, 3)", 115 , 455);

		gr.setColor(Color.YELLOW);
		gr.drawString("YOU LOST... ", 120 , 350);
		gr.drawString("PLAY AGAIN (1, 2, 3)", 120 , 450);
		
		w.repaint();

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
	}
	
	public void stopMusic() {
		
		sound.stop();
	}
	
	public void playSE(int i) throws LineUnavailableException {
		
		sound.setFile(i);
		sound.play();
	}
	
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_1) {
			try {

				active = false;
				w.removeKeyListener(this);
				Game.playing = true;
				Game.gamemode = 1; //Easy mode

			} 
			catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			}
		else if (key == KeyEvent.VK_2) { 
			try {

				active = false;
				w.removeKeyListener(this);
				Game.playing = true;
				Game.gamemode = 2; //Easy mode
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
		}
		else if (key == KeyEvent.VK_3) { 
			try {

				active = false;
				w.removeKeyListener(this);
				Game.playing = true;
				Game.gamemode = 3; //Easy mode
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}	
	}

	@Override
	public void keyReleased(KeyEvent e) { //If 1,2,3 play again
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}