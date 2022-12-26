import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
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
	static Menu menu;
	static Boolean playing = false; //Know if you are currently playing
	static int gamemode, x, y; //3 difficulty gamemodes
	static double dx = 0.1, dy = 0.1;
	int enemyCount;
	int lifeCount = 3;
	long timeOfLastBullet = System.currentTimeMillis();
	
	static Ship player;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>(); //Dinamic Array
	ArrayList<Bullet> bullets = new ArrayList<Bullet>(); //Dinamic Array
	
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
		initFont();
		
		System.out.println(" " +enemyCount);
		
		menu = new Menu(Game.window);
		
		menu.start();

		while(true) {
						
			moveObjects(); //Move screen objects 
			checkCollisions();//Detect collisions
			checkDeath();
			checkBounds();
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
	
	
	
	void initImages() { //Initializes images for the game
		try {
			Ship.img = ImageIO.read(new File("res/Ship1.png"));
			Enemy1.img = ImageIO.read(new File("res/Enemy1.png"));
			Enemy2.img = ImageIO.read(new File("res/Enemy2.png"));
			Bullet.img = ImageIO.read(new File("res/Bullet.png"));
			Game.img = ImageIO.read(new File("res/Background.png"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void initFont() {
		
		Font font;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("font/space_game.ttf"));
			font = font.deriveFont(Font.PLAIN, 20);
			graphics.setFont(font);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void initObjects() {
		
		player = new Ship(50, 50); //Init player
		enemyCount = 20*gamemode;
		
		for (int i = 0; i < enemyCount/2; i++) { //Init enemies1
			addEnemy(new Enemy1(600 + rand.nextInt(window.WIDTH), 
					rand.nextInt(window.HEIGHT), rand.nextInt(7)));
		}
		for (int j = 0; j < enemyCount/2; j++) { //Init and create enemies2
			addEnemy(new Enemy2(600 + rand.nextInt(window.WIDTH), 
					rand.nextInt(window.HEIGHT), rand.nextInt(7)));
		}
	}
	
	void moveObjects() {
		//y++;
		player.checkBorder();
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
		graphics.drawImage(img, 0, 0, window.WIDTH, window.HEIGHT, null);
		graphics.setColor(Color.YELLOW);
		graphics.drawString("Life: " + lifeCount, 60 , 60);

		player.paint(graphics);
		
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).paint(graphics); 
		}
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).paint(graphics);
		}

	}
	
	void scrollBackground() {
		
		x += dx;
		y += dy;
		
		
	}
	
	void checkDeath() {
		
		if (lifeCount == 0) {
			System.exit(0);
		}
		
	}
	
	void checkBounds() {
		for (int i = 0; i < bullets.size(); i++) {
			checkBulletBounds(bullets.get(i));
		}
		for (int i = 0; i < enemies.size(); i++) {
			checkEnemyBounds(enemies.get(i));
		}
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
	
	public void addEnemy(Enemy e) {
		enemies.add(e);		
	}
	
	public void removeEnemy(Enemy e) {
		enemies.remove(e);
	}
	
	public void addBullet(Bullet b) {
		bullets.add(b);
	}
	
	public void removeBullet(Bullet b) {
		bullets.remove(b);
	}
	
	public void checkBulletBounds(Bullet b) { //If bullet leaves the screen then destroy!
		if (b.x > window.WIDTH) {
			removeBullet(b);
			System.out.println("Bullet out");
		}
	}
	
	public void checkEnemyBounds(Enemy e) { //If bullet leaves the screen then destroy!
		if (e.x < -Enemy.WIDTH) {
			removeEnemy(e);
			System.out.println("Enemy out");
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_A) { //Left
			try {
				player.vx = -Ship.speedx;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}	
		else if (key == KeyEvent.VK_D) { //Right
			try {
				player.vx = Ship.speedx;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (key == KeyEvent.VK_W) { //Up
			player.vy = -Ship.speedy;
		}
		else if (key == KeyEvent.VK_S) { //Down
			player.vy = Ship.speedy;
		}
		else if (key == KeyEvent.VK_L) { //Player shooting
			
			long timeNow = System.currentTimeMillis();
			long time = timeNow - timeOfLastBullet;
			
			if (time < 0 || time > 1000) { //Max of 1 shot per second
			    timeOfLastBullet = timeNow;
			    bullets.add(new Bullet(20 + player.x, 20 + player.y));
			}
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
}
