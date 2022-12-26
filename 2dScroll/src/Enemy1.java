import java.awt.Rectangle;

public class Enemy1 extends Enemy{
	
	int speedx;
	int speedy;
	int WIDTH = 160;
	int HEIGHT = 120;

	Enemy1(int x, int y, int v) {
		super(x, y, v);
		// TODO Auto-generated constructor stub
	}
	

	void attack() {		
			
	}
	
	int hitBoxx = WIDTH - 60;
	int hitBoxy = HEIGHT - 80;
	
	public Rectangle getBounds() {
		return new Rectangle(x+20, y+20,  hitBoxx, hitBoxy);
	}
	
}

