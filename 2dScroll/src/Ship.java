import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Ship {
	
	int x,y;
	int vx;
	int vy;
	static int speedx = 4;
	static int speedy = 2;
	int WIDTH = 50;
	int HEIGHT = 30;
	
	static Image img;

	Ship(int x, int y) {
		this.x = x;
		this.y = y;
		
	}
	
	void move() {
		x += vx;
		y += vy;
		
	}
	
	void paint(Graphics gr) {
		
		gr.setColor(Color.BLACK);
		gr.drawRect(x, y, WIDTH, HEIGHT);
		gr.drawLine((int)(x + WIDTH*0.75), y,(int)(x + WIDTH*0.75), (int)(y+HEIGHT));
	}
	
	
}
