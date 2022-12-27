import java.awt.Graphics;
import java.awt.Image;

public class BulletEnemy extends Bullet{
	
	int WIDTH = 50;
	int HEIGHT = 50;
	
	static Image img;
		
	BulletEnemy(int x, int y){
		super(x, y);
	}
	
	void move() {
		x -= vx;
		
	}
	
	void paint(Graphics gr) {
		
		gr.drawImage(img, x, y, WIDTH, HEIGHT, null);
		getBounds();
	}
	
}
