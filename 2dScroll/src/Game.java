import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Game extends Thread implements KeyListener{
	
	
	static Window window;
	static Graphics graphics;
	static Image img;
	//int y;
	
	Ship player;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>(); //Dinamic Array
	ArrayList<Bullet> bullets = new ArrayList<Bullet>(); //Dinamic Array
	
	int enemyCount = 20;
	int lifeCount = 3;
	//Enemy enemies[] = new Enemy[5];
	Random rand = new Random();
	
	Game (Window w){
		window = w;
		graphics = w.gr;
		window.addKeyListener(this);
		window.setFocusable(true);
	}
	
	public void run() {
		//Initialize screen objects
		//y = 50;
		
		initObjects();
		
		initImages(); //Initialize images sprites for the game

		while(true) {
			
			moveObjects(); //Move screen objects 
			checkCollisions();//Detect collisions
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
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).move();
		}
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).move();
		}

	}
	
	void repaintScreen() { //Draw new line and repaint screen
		
		graphics.setColor(Color.BLACK);
		//graphics.fillRect(0, 0, window.WIDTH, window.HEIGHT);
		graphics.drawImage(img, 0, 0, window.WIDTH, window.HEIGHT, null);
		//w.gr.setColor(Color.BLUE);
		//w.gr.drawLine(50, 50, 700, y);
		player.paint(graphics);
		
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).paint(graphics); 
		}
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).paint(graphics);
		}

	}
	
	void initObjects() {
		
		player = new Ship(50, 50); //Init player
		
		for (int i = 0; i < enemyCount; i++) { //Init enemies1
			addEnemy(new Enemy(600 + rand.nextInt(window.WIDTH), 
					rand.nextInt(window.HEIGHT), rand.nextInt(7)));
		}
	}
	
	void shoot() {
		
	}
	
	void checkDeath() {
		
	}
	
	void checkCollisions() {
		
		for (int i = 0; i < enemies.size(); i++) {
			if (player.getBounds().intersects(enemies.get(i).getBounds())) { //Checks intersections with rectangles
				removeEnemy(enemies.get(i));
				lifeCount -= 1;
			}
			for (int j = 0; j < bullets.size(); j++) {
				if (bullets.get(j).getBounds().intersects(enemies.get(i).getBounds())) { //Checks intersections with rectangles
					removeEnemy(enemies.get(i));
					removeBullet(bullets.get(j));
				}
			}
		}
	}
	
	void initImages() { //Initializes images for the game
		try {
			Ship.img = ImageIO.read(new File("res/Ship1.png"));
			Enemy.img = ImageIO.read(new File("res/Ship2.png"));
			Bullet.img = ImageIO.read(new File("res/Bullet.png"));
			Game.img = ImageIO.read(new File("res/Background.png"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	void addEnemy(Enemy e) {
		enemies.add(e);
	}
	
	void removeEnemy(Enemy e) {
		enemies.remove(e);
	}
	
	void addBullet(Bullet b) {
		bullets.add(b);
	}
	
	void removeBullet(Bullet b) {
		bullets.remove(b);
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
			player.vx = -Ship.speedx;
		}	
		else if (key == KeyEvent.VK_D) {
			player.vx = Ship.speedx;
		}
		else if (key == KeyEvent.VK_W) {
			player.vy = -Ship.speedy;
		}
		else if (key == KeyEvent.VK_S) {
			player.vy = Ship.speedy;
		}
		else if (key == KeyEvent.VK_L) {
			bullets.add(new Bullet(20 + player.x, 20 + player.y));
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
		else if (key == KeyEvent.VK_W) {
			player.vy = 0;
		}
		else if (key == KeyEvent.VK_S) {
			player.vy = 0;
		}
	}
	
	
	//Enemy[] eraseEnemy(Enemy enemies [], int index) { //Function to erase an element from an array
//		
//		Enemy[] copy = new Enemy[enemies.length - 1]; //Create a copy -1 length
//		
//		int j = 0;
//		
//		for (int i = 0; i < enemies.length; i++) {
//			if (i != index) {
//				copy[j] = enemies[i]; //We copy everything but the element to remove
//				j++;
//			}
//		}	
//		return copy;	
//	}
}
