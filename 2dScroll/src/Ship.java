import java.awt.Color;
import java.awt.Graphics;

public class Ship {
	int x,y,v;
	int width = 50;
	int height = 30;

	Ship(int x, int y, int v) {
		this.x = x;
		this.y = y;
		this.v = v;
	}
	
	void move() {
		x += v;
	}
	
	void paint(Graphics gr) {
		gr.setColor(Color.BLACK);
		gr.drawRect(x, y, width, height);
		gr.drawLine((int)(x + width*0.75), y,(int)(x + width*0.75), (int)(y+height));
	}
	
	
}
