import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Enemy {
	
	int x,y;
	int speedx, speedy;
	static int WIDTH = 180;
	static int HEIGHT = 150;
	
	static Image img;

	long timeOfLastBullet = System.currentTimeMillis();
	
	Enemy(int x, int y, int v) {
		this.x = x;
		this.y = y;
		this.speedx = v;
		this.speedy = v;
	}
	
	void move() {
		
		x -= speedx;
	}
	
	void attack() {	
			
	}
	
	void paint(Graphics gr) {
		
		gr.drawImage(img, x, y, WIDTH, HEIGHT, null);

	}
	
	public Rectangle getBounds() {
		return new Rectangle(x+20, y+20,  WIDTH - 60, HEIGHT - 80);
	}

}
