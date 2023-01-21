
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JTextField;

public class setName extends Thread implements KeyListener{
	
	//Extends storyScreen as its just another screen before the start
	//Of the game which will only be executed when you first open de exe
	
	Game game;
	Window w;
	Graphics gr;
	static String name;
	static Image img;
	static boolean active = true;
	static Sound sound;
	int counter = 0;

	
	setName(Window w){
		this.w = w;
		this.gr = w.gr;
		w.addKeyListener(this);
		
	}
	
	public void run() {
		
		initImages();
		initFont();
		writeName();
		System.out.println(" "+gameOver.name);
						
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
	
	public void initImages() {
		
		try {

			setName.img = ImageIO.read(new File("res/BackgroundBlurr.png"));
			
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
	
	public void repaintScreen() {
		
		gr.setColor(Color.BLACK);
		gr.drawImage(img, 0, 0, Window.WIDTH, Window.HEIGHT, null);
		//graphics.drawImage(img, x, 0, window.WIDTH, window.HEIGHT, null);
		gr.setColor(Color.DARK_GRAY);
		gr.drawString("PILOT, WHAT'S YOUR NAME? ", 65 , 355);

		gr.setColor(Color.YELLOW);
		
		gr.drawString("PILOT, WHAT'S YOUR NAME? ", 70 , 350);

		
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
	
	public void writeName() {
		
		boolean nameEntered = false;
		
		while(!nameEntered) {
			
			JTextField nameField = new JTextField();
			nameField.setColumns(20);
			nameField.setBounds(80, 360, 200, 30); // set the size and location of the text field

			w.add(nameField); // add the text field to the Window

			nameField.addActionListener(new ActionListener() {
				
			  public void actionPerformed(ActionEvent evt) {
			    String temp = nameField.getText();
			    gameOver.name = temp; //Store it in the name variable
			    //nameEntered = true;
			    w.remove(nameField); //remove the text field when the user press enter
			  }
			  
			});
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
