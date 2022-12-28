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

public class Menu extends Thread implements KeyListener{
	
	Window w;
	Graphics gr;
	static Image img;
	static Game game;
	static boolean active = true;
	
	public Menu(Window w) {
		this.w = w;
		this.gr = w.gr;
		w.addKeyListener(this);
	}
	
	public void run() {//Menu loop, similar procedure to the game class
				
		initImages();
		initFont();
						
		while(active) {
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Eee funcionant + " +active);
			repaintScreen();
			w.repaint();
			
		}
		
		w.removeKeyListener(this);
				
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
			font = Font.createFont(Font.TRUETYPE_FONT, new File("font/space_game.ttf"));
			font = font.deriveFont(Font.PLAIN, 50);
			gr.setFont(font);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void repaintScreen() { //Paint Menu elements
		
		gr.setColor(Color.BLACK);
		gr.drawImage(img, 0, 0, w.WIDTH, w.HEIGHT, null);
		//graphics.drawImage(img, x, 0, window.WIDTH, window.HEIGHT, null);
		gr.setColor(Color.YELLOW);
		gr.drawString("EASY MODE (PRESS 1) ", 200 , 300);
		gr.drawString("NORMAL MODE (PRESS 2) ", 200 , 400);
		gr.drawString("HARD MODE (PRESS 3) ", 200 , 500);

		
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
				game = new Game(w);
				active = false;
				w.removeKeyListener(this);
				game.start();
				Game.gamemode = 1; //Easy mode
			} 
			catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			}
		else if (key == KeyEvent.VK_2) { 
			try {
				game = new Game(w);
				active = false;
				game.start();
				Game.gamemode = 2; //Medium	
				w.removeKeyListener(this);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
		}
		else if (key == KeyEvent.VK_3) { 
			try {
				game = new Game(w);
				Game.gamemode = 3; //Hard mode
				game.start();
				active = false;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_1) {
			active = false;
		}
			
		else if (key == KeyEvent.VK_2) {
			active = false;
		
		}
		else if (key == KeyEvent.VK_3) {
			active = false;
		}

	}
	
	

}
