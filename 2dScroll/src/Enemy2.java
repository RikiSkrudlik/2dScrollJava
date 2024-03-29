import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Enemy2 extends Enemy {
	
	int speedx;
	int speedy;
	int WIDTH = 160;
	int HEIGHT = 120;
	
	static Image img;

	Enemy2(int x, int y, int v) {
		super(x, y, v);
	}
	
	void attack() {
		
		Game.enemyBullets.add(new BulletEnemy(20 + this.x, 20 + this.y));
	}
	
	void paint(Graphics gr) {
		
		gr.drawImage(img, x, y, WIDTH, HEIGHT, null);
	}
	
	public Rectangle getBounds() {
		
		return new Rectangle(x+20, y+20,  WIDTH - 60, HEIGHT - 80);
	}
	
}
