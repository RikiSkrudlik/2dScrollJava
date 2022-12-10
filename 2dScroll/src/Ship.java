import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Ship {
	
	int x,y;
	int vx;
	int vy;
	static int speed = 2;
	int WIDTH = 50;
	int HEIGHT = 30;
	
	static Image img;

	Ship(int x, int y) {
		this.x = x;
		this.y = y;
		
	}
	
	void move() {
		x += vx;
		
	}
	
	void paint(Graphics gr) {
		
		gr.setColor(Color.BLACK);
		gr.drawRect(x, y, WIDTH, HEIGHT);
		gr.drawLine((int)(x + WIDTH*0.75), y,(int)(x + WIDTH*0.75), (int)(y+HEIGHT));
	}
	
	
}
