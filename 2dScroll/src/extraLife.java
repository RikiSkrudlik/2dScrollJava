import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class extraLife {

	static Image img;
	int x, y, WIDTH = 40, HEIGHT = 40, speedx = 5; //Position and dimensions
	
	extraLife(int x, int y){

		this.x = x;
		this.y = y;
	}
	
	void paint(Graphics gr) {
		
		gr.drawImage(img, x, y, WIDTH, HEIGHT, null);
		gr.setColor(Color.ORANGE);
		gr.drawRect(x, y, hitBoxx, hitBoxy);

		getBounds();
	}
	
	void move() {
		
		x -= speedx;

	}
	
	int hitBoxx = WIDTH;
	int hitBoxy = HEIGHT;
	
	public Rectangle getBounds() {
		return new Rectangle(x, y,  hitBoxx, hitBoxy);
	}
	
	
}
