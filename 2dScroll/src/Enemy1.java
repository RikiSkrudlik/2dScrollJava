import java.awt.Rectangle;

public class Enemy1 extends Enemy{
	
	int WIDTH = 160;
	int HEIGHT = 120;
	
	long lastMove = System.currentTimeMillis();

	int correction = 1;
	
	Enemy1(int x, int y, int v) {
		super(x, y, v);
		// TODO Auto-generated constructor stub
	}
	

	void attack() {	
		
			
	}
	
	void move() {
		
		x -= speedx;
		
		//System.out.println("Velocitat " + speedx);
		
		//Now create an alterning up and down movement, every 2 seconds switch direction
		
//		while (System.currentTimeMillis() - lastMove >= 3000) {
//						
//		    // Change direction here
//			//System.out.println("Direcció "+correction);
//			y -= speedy*correction;
//		    //lastMove = System.currentTimeMillis();
//		    //if ()
//		}

	}
	
	int hitBoxx = WIDTH - 60;
	int hitBoxy = HEIGHT - 80;
	
	public Rectangle getBounds() {
		return new Rectangle(x+20, y+20,  hitBoxx, hitBoxy);
	}
	
}

