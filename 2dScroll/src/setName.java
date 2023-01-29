
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class setName extends Thread implements KeyListener{
	
	//Extends storyScreen as its just another screen before the start
	//Of the game which will only be executed when you first open de exe
	
	Game game;
	Window w;
	Graphics gr;
	static String name;
	static TextField nameField = new TextField();
	static Image img;
	static boolean active = true;
	static Sound sound;
	int counter = 0;

	
	setName(Window w){
		this.w = w;
		this.gr = w.gr;
		gr.setColor(Color.YELLOW);
//		nameField.setColumns(20);
//		nameField.setBounds(350, 400, 200, 70); // set the size and location of the text field
//		nameField.setFont(gr.getFont());
//		nameField.setForeground(Color.YELLOW);
//		nameField.setBackground(new Color(0,0,0,0)); //set transparent
//		nameField.addActionListener(new ActionListener() {
//		    public void actionPerformed(ActionEvent e) {
//		        
//		    	name = nameField.getText();
//		    	w.remove(nameField);
//		    	active = false;
//		    }
//		});
		w.add(nameField); // add the text field to the Window
		w.addKeyListener(this);
		
	}
	
	public void run() {
		
		System.out.println("Bines!");
		
		initImages();
		initFont();
						
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
		
		System.out.println("Hola");
		//w.remove(nameField);
		Menu.stopMusic(); //Stop the intro music 
		fadeOut();	
		game = new Game(w, name);
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

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ENTER) {
			try { //We enter set name
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
		
	}

	

	
	

}
