import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Ship {
	
	int x,y;
	int vx;
	int vy;
	static int speedx = 11;
	static int speedy = 8;
	int WIDTH = 150;
	int HEIGHT = 90;
	
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
		
		gr.drawImage(img, x, y, WIDTH, HEIGHT, null);
		gr.setColor(Color.ORANGE);

		gr.drawRect(x, y, WIDTH-30, HEIGHT -35);
		getBounds();
//		gr.setColor(Color.BLACK);
//		gr.drawRect(x, y, WIDTH, HEIGHT);
//		gr.drawLine((int)(x + WIDTH*0.75), y,(int)(x + WIDTH*0.75), (int)(y+HEIGHT));
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, WIDTH - 30, HEIGHT - 35);
	}
	
	
}
