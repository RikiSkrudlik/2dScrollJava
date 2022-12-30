import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;

public class Game extends Thread implements KeyListener{
	
	
	static Window window;
	static Graphics graphics;
	static BufferedImage img;
	static Menu menu;
	static Boolean playing = true; //Know if you are currently playing
	static int gamemode, x = 0, y; //3 difficulty gamemodes
	static double dx = 1, dy = 0.1;
	int enemyCount;
	static Sound sound;
	int lifeCount = 3;
	long initialTime = System.currentTimeMillis();
	
	static Ship player;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>(); //Dinamic Array
	ArrayList<Bullet> bullets = new ArrayList<Bullet>(); //Dinamic Array
	static ArrayList<BulletEnemy> enemyBullets = new ArrayList<BulletEnemy>(); //Dinamic Array
	
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
		
		player = new Ship(50, 50); //Init player
		initImages(); //Initialize images sprites for the game
		initFont();
		try {
			initSounds();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println(" " +enemyCount);
		
		long timeStart = initialTime;
		
		while(playing) {
			
			//We will be creating enemies every x seconds in random positions
			//In front of the player
			
			long timeNow = System.currentTimeMillis();
			long time = timeNow - timeStart;
			
			if (time < 0 || time > 5000) { //Every 5 seconds create enemies
			    createEnemies(gamemode);
			    timeStart = timeNow;
			    
			}
			
			repaintScreen(); //Repaint screen			
			moveObjects(); //Move screen objects
			checkCollisions();//Detect collisions
			enemiesAttack();
			checkDeath();
			checkBounds();
			
			
			try {
				Thread.sleep(10);
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
			BulletEnemy.img = ImageIO.read(new File("res/BulletEnemy.png"));
			Game.img = ImageIO.read(new File("res/Background.png"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void initFont() {
		
		Font font;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("font/arcade.ttf"));
			font = font.deriveFont(Font.PLAIN, 20);
			graphics.setFont(font);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void createEnemies(int gamemode) {
		
		enemyCount = 4*gamemode;
		
		for (int i = 0; i < enemyCount/2; i++) { //Init enemies1
			addEnemy(new Enemy1(600 + rand.nextInt(window.WIDTH), 
					rand.nextInt(window.HEIGHT), rand.nextInt(2,4)));
		}
		for (int j = 0; j < enemyCount/2; j++) { //Init and create enemies2
			addEnemy(new Enemy2(600 + rand.nextInt(window.WIDTH), 
					rand.nextInt(window.HEIGHT), rand.nextInt(2,4)));
		}
	}
	
	void initSounds() throws LineUnavailableException {
		
		sound = new Sound();
		//playMusic(0);

	}
	
	public void playMusic(int i) throws LineUnavailableException {
		
		sound.setFile(i);
		sound.play();
		sound.loop();
	}
	
	public void stopMusic() {
		sound.stop();
	}
	
	public void playSE(int i) throws LineUnavailableException {
		
		sound.setFile(i);
		sound.play();
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
		for (int i = 0; i < enemyBullets.size(); i++) {
			enemyBullets.get(i).move();
		}

	}
	
	long lastAttack = initialTime;
	
	void enemiesAttack() {
		
		long timeNow = System.currentTimeMillis();
		long time = timeNow - lastAttack;
		
		int max = 9;
		int min = 2;
		
		int randomizer = (int)(Math.random()*(max-min+1)+min); 
		
		if (time < 0 || time > 10000/randomizer) { //Enemy shooting is random
		    lastAttack = timeNow;
		    
		    for (int i = 0; i < enemies.size(); i++) {
		    	
				int randomizer2 = (int)(Math.random()*(4)+1); 

				//We create a random 1/4 chance for the specific enemy to shoot
				
				if (randomizer2 == 3) { 
					enemies.get(i).attack();
				}
		    }
		}
		
	}
	
	void repaintScreen() { //Draw new line and repaint screen
		
		graphics.setColor(Color.BLACK);
		graphics.drawImage(img, 0, 0, window.WIDTH, window.HEIGHT, null);
		//graphics.drawImage(img, -img.getWidth() + x, 0, window.WIDTH, window.HEIGHT, null);
		graphics.setColor(Color.YELLOW);
		graphics.drawString("Life: " + lifeCount, 60 , 60);
		
		x += dx;
		y += dy;
				
		if (x > img.getWidth()) {
	        x = 0;
	        System.out.println("Retornant..");
	    }

		player.paint(graphics);
		
		for (int i = 0; i < enemies.size(); i++) { //Paint enemies
			enemies.get(i).paint(graphics); 
		}
		for (int i = 0; i < bullets.size(); i++) { //Paint your bullets
			bullets.get(i).paint(graphics);
		}
		for (int i = 0; i < enemyBullets.size(); i++) { //Paint enemy bullets
			enemyBullets.get(i).paint(graphics);
		}
		
		window.repaint(); 


	}
	
	void scrollBackground() {
		
			
	}
	
	void checkDeath() { //Simple lifecount if equals to 0 game over and show endScreen
		
		if (lifeCount == 0) {
			gameOver endScreen = new gameOver(window);
			playing = false;
			endScreen.start();
		}		
	}
	
	void checkBounds() { //If out of bounds then make disappear
		for (int i = 0; i < bullets.size(); i++) {
			checkBulletBounds(bullets.get(i));
		}
		for (int i = 0; i < enemyBullets.size(); i++) {
			checkBulletBounds(enemyBullets.get(i));
		}
		for (int i = 0; i < enemies.size(); i++) {
			checkEnemyBounds(enemies.get(i));
		}
	}
	
	void checkCollisions() {
		
		for (int i = 0; i < enemies.size(); i++) {
			if (player.getBounds().intersects(enemies.get(i).getBounds()) ) { //Checks intersections with rectangles
				removeEnemy(enemies.get(i));
				lifeCount -= 1;
			}
			for (int j = 0; j < bullets.size(); j++) { 
				if (bullets.get(j).getBounds().intersects(enemies.get(i).getBounds())) { //Checks intersections with rectangles
					removeEnemy(enemies.get(i));
					removeBullet(bullets.get(j));
				}
			}
			for (int j = 0; j < enemyBullets.size(); j++) { 
				if (enemyBullets.get(j).getBounds().intersects(player.getBounds())) {
					lifeCount -= 1;
					removeEnemyBullet(enemyBullets.get(j));
				}
			}
		}
	}
	
	
	
	
	/*
	 * Check bounds, remove... Events for the playing mechanics.
	 * 
	*/
	
	
	
	
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
	public void addEnemyBullet(BulletEnemy b) {
		enemyBullets.add(b);
	}
	
	public void removeEnemyBullet(BulletEnemy b) {
		enemyBullets.remove(b);
	}
	
	public void checkBulletBounds(Bullet b) { //If bullet leaves the screen then destroy!
		if (b.x > window.WIDTH) {
			removeBullet(b);
		}
	}
	
	public void checkEnemyBounds(Enemy e) { //If bullet leaves the screen then destroy!
		if (e.x < -Enemy.WIDTH) {
			removeEnemy(e);
			//System.out.println("Enemy out");
		}
	}
	
	/*
	 * All key listener events, for the ship moving and shooting
	 * 
	*/
	
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
			
			long lastBullet = initialTime;
			long timeNow = System.currentTimeMillis();
			long time = timeNow - lastBullet;
			
			if (time < 0 || time > 500) { //Max of 1 shot per second
			    lastBullet = timeNow;
			    bullets.add(new Bullet(20 + player.x, 20 + player.y));
			    try {
					playSE(4);
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
