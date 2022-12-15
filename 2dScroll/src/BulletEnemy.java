import java.awt.Color;
import java.awt.Graphics;

public class BulletEnemy extends Bullet{
	
	int WIDTH = 50;
	int HEIGHT = 50;
	
	BulletEnemy(int x, int y){
		super(x, y);
	}
	
	void move() {
		x += -vx;
	}
	
	void paint(Graphics gr) {
		
		gr.drawImage(img, x, y, WIDTH, HEIGHT, null);
		gr.setColor(Color.BLACK);
		gr.drawRect(x, y, WIDTH-15, HEIGHT -15);
		getBounds();
	}
}
