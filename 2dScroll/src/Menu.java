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
	
	public Menu(Window w) {
		this.w = w;
		this.gr = w.gr;
		w.addKeyListener(this);
	}
	
	public void run() {//Menu loop, similar procedure to the game class
		
		initImages();
		initFont();
		
		while(true) {
			
			repaintScreen();
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
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
			font = font.deriveFont(Font.PLAIN, 20);
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
		gr.drawString("Life: " , 60 , 60);

		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
