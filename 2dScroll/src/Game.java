import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends Thread implements KeyListener{
	
	
	static Window window;
	static Graphics graphics;
	//int y;
	Ship player;
	
	Game (Window w){
		window = w;
		graphics = w.gr;
		window.addKeyListener(this);
		window.setFocusable(true);
	}
	
	public void run() {
		//Initialize screen objects
		//y = 50;
		
		player = new Ship(50, 50);

		while(true) {
			
			moveObjects(); //Move screen objects 
			//Detect collisions
			repaintScreen(); //Repaint screen
			window.repaint(); 
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	void moveObjects() {
		//y++;
		player.move();

	}
	
	void repaintScreen() { //Draw new line and repaint screen
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, window.WIDTH, window.HEIGHT);
		//w.gr.setColor(Color.BLUE);
		//w.gr.drawLine(50, 50, 700, y);
		player.paint(graphics);

	}
	
	void initialize() {
		
	}
	
	void shoot() {
		
	}
	
	void checkDeath() {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_A) {
			player.vx = -Ship.speed;
		}
			
		else if (key == KeyEvent.VK_D) {
			player.vx = Ship.speed;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		 //TODO Auto-generated method stub
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_A) {
			player.vx = 0;
		}
			
		else if (key == KeyEvent.VK_D) {
			player.vx = 0;
		
	}
	}
}
