import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Ship {
	
	int x,y;
	int vx;
	int vy;
	static int speedx = 8;
	static int speedy = 6;
	int WIDTH = 150;
	int HEIGHT = 90;
	
	static Image img;

	Ship(int x, int y) {
		this.x = x;
		this.y = y;
		
	}
	
	void move() {
		if (x < 15 && vx < 0) { //Left side collision
			return;
		}
		if (x > Game.window.WIDTH - WIDTH && vx > 0) {
			return;
		}
		if (y < 15 && vy < 0) {
			return; 
		}
		if (y > Game.window.HEIGHT - HEIGHT && vy > 0) {
			return;
		}
		x += vx;
		y += vy;
		
	}
	
	void paint(Graphics gr) {
		
		gr.drawImage(img, x, y, WIDTH, HEIGHT, null);
		//gr.setColor(Color.ORANGE);

		//gr.drawRect(x, y, WIDTH-30, HEIGHT -35);
		getBounds();

	}
	
	void checkBorder() {//To check if ship touches border
		if (x == Game.window.WIDTH) {
			vx = 0;
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, WIDTH - 30, HEIGHT - 35);
	}
	
	
}
