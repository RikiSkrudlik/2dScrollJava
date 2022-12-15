import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Image;

public class Bullet {
	
	int x;
	int y;
	int vx = 18;
	int WIDTH = 50;
	int HEIGHT = 50;
	
	static Image img;
	
	public Bullet(int x, int y) {
		
		this.x = x;
		this.y = y;
	}
	
	void move() {
		x += vx;
		
	}
	
	void paint(Graphics gr) {
		
		gr.drawImage(img, x, y, WIDTH, HEIGHT, null);
		gr.setColor(Color.ORANGE);
		gr.drawRect(x, y, WIDTH-15, HEIGHT -15);
		getBounds();
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, WIDTH - 15, HEIGHT - 10);
	}
	
}
