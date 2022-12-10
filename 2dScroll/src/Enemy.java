import java.awt.Color;
import java.awt.Graphics;

public class Enemy {
	
	int x,y;
	int v = 8;
	int width = 30;
	int height = 30;

	Enemy(int x, int y, int v) {
		this.x = x;
		this.y = y;
	}
	
	void move() {
		y -= v;
	}
	
	void attack() {
		
	}
	
	void paint(Graphics gr) {
		
		gr.setColor(Color.RED);
		gr.drawRect(x, y, width, height);
		gr.drawLine((int)(x + width*0.75), y,(int)(x + width*0.75), (int)(y+height));
	}

}
