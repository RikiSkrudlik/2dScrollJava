import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Enemy2 {
	
	int x,y;
	int speedx;
	int speedy;
	int WIDTH = 160;
	int HEIGHT = 120;
	
	static Image img;

	Enemy2(int x, int y, int v) {
		this.x = x;
		this.y = y;
		this.speedx = v;
	}
	
	void move() {
		x -= speedx;
	}
	
	void attack() {
		
	}
	
	void paint(Graphics gr) {
		
		gr.drawImage(img, x, y, WIDTH, HEIGHT, null);
		gr.setColor(Color.ORANGE);
		gr.drawRect(x+45, y+40, hitBoxx, hitBoxy);

		getBounds();
	}
	
	int hitBoxx = WIDTH - 60;
	int hitBoxy = HEIGHT - 80;
	
	public Rectangle getBounds() {
		return new Rectangle(x+20, y+20,  hitBoxx, hitBoxy);
	}
	
}
