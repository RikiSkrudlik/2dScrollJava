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
	BufferedImage img, img2;
	int backgroundWidth, backgroundHeight;
	static Menu menu;
	static Boolean playing = true, playerHit = false; //Know if you are currently playing
	static int gamemode; 
	int x = 0, y = 0, x2 = Window.WIDTH; //3 difficulty gamemodes
	static double dx = 1, dy = 0.1;
	int enemyCount;
	static Sound sound;
	int lifeCount = 3;
	static long initialTime;
	static long counter, extra, scoreDeath;
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

	BufferedImage hitScreen = new BufferedImage(Window.WIDTH, Window.HEIGHT, BufferedImage.TYPE_INT_ARGB);
	
	public void run() {
		//Initialize screen objects
		//y = 50;
		
		player = new Ship(50, 50); //Init player
		initialTime = System.currentTimeMillis();
		initImages(); //Initialize images sprites for the game
		initFont();
		
		try {
			initSounds();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
			
			counter = (timeNow - initialTime)/100 + extra;
						
			try {
				checkCollisions();
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}//Detect collisions
			checkBounds();
			checkDeath();
			enemiesAttack();
			repaintScreen(); //Repaint screen
			moveObjects(); //Move screen objects
			
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		extra = 0;
	}
	
	void initImages() { //Initializes images for the game
		try {
			Ship.img = ImageIO.read(new File("res/Ship1.png"));
			Enemy1.img = ImageIO.read(new File("res/Enemy1.png"));
			Enemy2.img = ImageIO.read(new File("res/Enemy2.png"));
			Bullet.img = ImageIO.read(new File("res/Bullet.png"));
			BulletEnemy.img = ImageIO.read(new File("res/BulletEnemy.png"));
			img = ImageIO.read(new File("res/Background800.png"));
			img2 = ImageIO.read(new File("res/Background800.png"));
			Graphics g = hitScreen.getGraphics();
			g.setColor(new Color(255, 0, 0, 50)); //Red color with 50% alpha, for the hit animation
			g.fillRect(0, 0, hitScreen.getWidth(), hitScreen.getHeight());
			backgroundWidth = Window.WIDTH;
			backgroundHeight = Window.HEIGHT;
			
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
		
		enemyCount = 3*gamemode; //Theamount of enemies displayed (difficulty)
		
		for (int i = 0; i < enemyCount/2; i++) { //Init enemies1
			addEnemy(new Enemy1(600 + rand.nextInt(Window.WIDTH), 
					rand.nextInt(Window.HEIGHT), rand.nextInt(5,8)));
		}
		for (int j = 0; j < enemyCount/2; j++) { //Init and create enemies2
			addEnemy(new Enemy2(600 + rand.nextInt(Window.WIDTH), 
					rand.nextInt(Enemy.HEIGHT, Window.HEIGHT - Enemy.HEIGHT), rand.nextInt(2,4)));
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
		
		//Created 2 separate images instead of 1 image doubling the size as it had resizing issues
		
		graphics.drawImage(img, x, y, backgroundWidth, backgroundHeight, null);
		graphics.drawImage(img, x2, y, backgroundWidth, backgroundHeight, null);
		
		x -= 1;
		x2 -= 1;
		
		if (x < -Window.WIDTH) {
			x = backgroundWidth;
		}
		if (x2 < -Window.WIDTH) {
			x2 = Window.WIDTH;
		}
		
		if (playerHit) {
			  graphics.drawImage(hitScreen, 0, 0, null); //Draw hitScreen over the top of the game screen
			  playerHit = false;
		}
		
		graphics.setColor(Color.YELLOW);
		graphics.drawString("Life: " + lifeCount, 60 , 60);
		graphics.drawString("Score: " + counter, 560 , 60);

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
	
	void checkDeath() { //Simple lifecount if equals to 0 game over and show endScreen
		
		if (lifeCount == 0) {
			gameOver endScreen = new gameOver(window);
			playing = false;
			window.removeKeyListener(this);
			gameOver.active = true;
			endScreen.start();
		}		
	}
	
	void checkBounds() { //If out of bounds then make disappear
		for (int i = 0; i < bullets.size(); i++) {
			checkBulletBounds(bullets.get(i));
		}
		for (int i = 0; i < enemyBullets.size(); i++) {
			checkEnemyBulletBounds(enemyBullets.get(i));
		}
		for (int i = 0; i < enemies.size(); i++) {
			checkEnemyBounds(enemies.get(i));
		}
	}
	
	void checkCollisions() throws LineUnavailableException {
		
		/*
		 * In this function we check using the getBounds function we created
		 * to check the possible collisions, we store the indexes of the enemies 
		 * affected and then we erase them, we do it in another loop to have 
		 * no problems with out of bounds indexes
		 */
		
		ArrayList<Enemy> enemiesRemoved = new ArrayList<Enemy>(); //Dinamic Array
		
		for (int i = 0; i < enemies.size(); i++) {
			if (player.getBounds().intersects(enemies.get(i).getBounds()) ) { //Checks intersections with rectangles
				removeEnemy(enemies.get(i));
				playSE(5);
				playerHit = true;
				lifeCount -= 1;
			}
			for (int j = 0; j < bullets.size(); j++) { 
				if (bullets.get(j).getBounds().intersects(enemies.get(i).getBounds())) { //Checks intersections with rectangles
					//removeEnemy(enemies.get(i));
					enemiesRemoved.add(enemies.get(i));
					removeBullet(bullets.get(j));
					extra += 100; //Get 100 extra points

				}
			}
			for (int j = 0; j < enemyBullets.size(); j++) { 
				if (enemyBullets.get(j).getBounds().intersects(player.getBounds())) {
					lifeCount -= 1;
					//playSE(5);
					playerHit = true;
					removeEnemyBullet(enemyBullets.get(j));
				}
			}
			for (int j = 0; j < enemiesRemoved.size(); j++) {
				removeEnemy(enemiesRemoved.get(j));
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
		if (b.x > Window.WIDTH) {
			removeBullet(b);
			//System.out.println("bala fora");
		}
	}
	
	public void checkEnemyBulletBounds(BulletEnemy b) { //If bullet leaves the screen then destroy!
		if (b.x > Window.WIDTH) {
			removeEnemyBullet(b);
			//System.out.println("bala enemiga fora");
		}
	}
	
	public void checkEnemyBounds(Enemy e) { //If bullet leaves the screen then destroy!
		if (e.x < -Enemy.WIDTH) {
			removeEnemy(e);
			extra -= 100;
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
			
			if (time < 0 || time > 900) { //Max of 1 shot per second
			    lastBullet = timeNow;
			    bullets.add(new Bullet(20 + player.x, 20 + player.y));
//			    try {
//					playSE(4);
//				} catch (LineUnavailableException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
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