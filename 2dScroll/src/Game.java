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
	static Menu menu;
	
	BufferedImage img, img2;
	BufferedImage hitScreen = new BufferedImage(Window.WIDTH, Window.HEIGHT, BufferedImage.TYPE_INT_ARGB);

	static Boolean playing = true, playerHit = false, lifeHit = false, ulti = false, activeUlti = false; //Know if you are currently playing
	static int gamemode, fadeCounter = 0, intermitent = 4;  //For the background scrolling
	int enemyCount, enemiesKilled;
	static Sound sound;
	int lifeCount = 3, ultiNumber = 16, x = 0, y = 0, x2 = Window.WIDTH, backgroundWidth, backgroundHeight; //number of enemies needed for ulti
	static long counter, extra, scoreDeath, initialTime;
	static Ship player;
	static extraLife life;
	//Various arrays for enemies bullets...
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
	
	public void checkUlti() {//If you killed 15 enemies you allowed to ulti
		if (enemiesKilled == ultiNumber) {
			activeUlti = true;
		}
	}
	
	public void run() {
		//Initialize screen objects
		
		initialTime = System.currentTimeMillis();
		try {
			initMultiple();
			initSounds();
		} catch (IOException | LineUnavailableException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		initImages(); //Initialize images sprites for the game
		initFont();
		
		fadeIn();
		
		long timeStart = initialTime;
		
		while(playing) {
			
			//We will be creating enemies every x seconds in random positions
			//In front of the player
			
	        //drawFadeBlackScreen();

			
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
			moveObjects(); //Move screen objects
			checkBounds();
			checkDeath();
			enemiesAttack();
			checkUlti();
			if (life == null) { //if there isn't a life created (so it doesn't erase the last one)
				createLife();
			}
			
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			repaintScreen(); //Repaint screen

		}
		
		extra = 0;
		stopMusic();
	}
	
	public void fadeIn() {
	    for (int i = 0; i <= 255; i += 5) {
	        graphics.setColor(new Color(0, 0, 0, 255-i));
	        graphics.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
	        window.repaint();
	        try {
	            Thread.sleep(25);
	        } catch (InterruptedException e) {
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
			img = ImageIO.read(new File("res/Background800.png"));
			img2 = ImageIO.read(new File("res/Background800.png"));
			extraLife.img = ImageIO.read(new File("res/extraLife.png"));
			backgroundWidth = Window.WIDTH;
			backgroundHeight = Window.HEIGHT;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void initMultiple() throws IOException {
		
		player = new Ship(50, 50); //Init player
		player.initTurbo();
		
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
	
	void initSounds() throws LineUnavailableException {
		
		sound = new Sound();
		playMusic(0);
	}
	
	void createLife() {
		
		long timeNow = System.currentTimeMillis();
		long time = timeNow - lastAttack;
		
		int max = 9;
		int min = 2;
		
		int randomizer = (int)(Math.random()*(max-min+1)+min); 
		
		if (time < 0 || time > 9000/randomizer) { //Enemy shooting is random
		    lastAttack = timeNow;

			int randomizer2 = (int)(Math.random()*(4)+1); 

				//We create a random 1/4 chance for the specific enemy to shoot
				
			if (randomizer2 == 3) { 
					
				life = new extraLife(600 + rand.nextInt(Window.WIDTH), 
					rand.nextInt(Window.HEIGHT - extraLife.HEIGHT));
					
				}
		    }
	}
		
	
	void createEnemies(int gamemode) {
		
		enemyCount = 3*gamemode; //Theamount of enemies displayed (difficulty)
		
		for (int i = 0; i < enemyCount/2; i++) { //Init enemies1
			addEnemy(new Enemy1(Window.WIDTH + rand.nextInt(Window.WIDTH), 
					rand.nextInt(Window.HEIGHT - Enemy.HEIGHT), rand.nextInt(5,8)));
		}
		for (int j = 0; j < enemyCount/2; j++) { //Init and create enemies2
			addEnemy(new Enemy2(Window.WIDTH + rand.nextInt(Window.WIDTH), 
					rand.nextInt(Enemy.HEIGHT, Window.HEIGHT - Enemy.HEIGHT), rand.nextInt(4,7)));
		}
	}
	
	void moveObjects() {
		//y++;
		player.checkBorder();
		player.move();
		if (life != null) {
			life.move();
		}
		
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
		
		x -= 2;
		x2 -= 2;
		
		if (x < -Window.WIDTH) {
			x = backgroundWidth;
		}
		if (x2 < -Window.WIDTH) {
			x2 = Window.WIDTH;
		}
				
		//TOP FOR SCROLLING BACKGROUND ONLY
		
		if (playerHit) {
			Color red = new Color(255, 0, 0, 50);
			  // draw a green rectangle over the entire screen
			 graphics.setColor(red);
			 graphics.fillRect(0, 0, Window.WIDTH, Window.HEIGHT); //Draw hitScreen over the top of the game screen
			 playerHit = false;
		}
		
		if (lifeHit) {
			Color green = new Color(0, 255, 0, 50);
			  // draw a green rectangle over the entire screen
			 graphics.setColor(green);
			 graphics.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
			 lifeHit = false;
		}
		
		graphics.setColor(Color.YELLOW);
		graphics.drawString("Life: " + lifeCount, 60 , 60);
		graphics.drawString("Score: " + counter, 560 , 60);
		//graphics.drawString("Enemies: " + enemiesKilled, 260 , 160);
		
		//Paint ulti sign
		
		if (enemiesKilled >= 4) {
			graphics.drawString("U", 280 , 60);
		}
		if (enemiesKilled >= 8) {
			graphics.drawString("L", 300 , 60);
		}
		if (enemiesKilled >= 12) {
			graphics.drawString("T", 320 , 60);
		}
		if (enemiesKilled >= 16) {
			graphics.drawString("I", 340 , 60);
		}

		if (activeUlti && intermitent%4 == 0) {
			
			graphics.drawRect(260, 80, 100, 40);
			
		}
		intermitent++;
		player.paint(graphics);
		
		if (life != null) { //If there isn't a life in the game rn
			life.paint(graphics);
		}
		
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
				enemiesRemoved.add(enemies.get(i));
				playSE(5);
				playerHit = true;
				lifeCount -= 1;
			}
			for (int j = 0; j < bullets.size(); j++) { 
				if (bullets.get(j).getBounds().intersects(enemies.get(i).getBounds())) { //Checks intersections with rectangles
					//removeEnemy(enemies.get(i));
					enemiesRemoved.add(enemies.get(i));
					removeBullet(bullets.get(j));
					extra += 100;
					enemiesKilled += 1; //Get 100 extra points

				}
			}
			for (int j = 0; j < enemyBullets.size(); j++) { 
				if (enemyBullets.get(j).getBounds().intersects(player.getBounds())) {
					lifeCount -= 1;
					playSE(5);
					playerHit = true;
					removeEnemyBullet(enemyBullets.get(j));
				}
			}
			for (int j = 0; j < enemiesRemoved.size(); j++) {
				removeEnemy(enemiesRemoved.get(j));
			}
		}
		//Check contact with extralife
		if (life != null && life.getBounds().intersects(player.getBounds())) { //Checks intersections with rectangles);
			
			lifeHit = true;
			lifeCount += 1;
			playSE(6);
			life = null;

		}
		//Not a collision but put it here
		
	}
	
	
	

	
	
	/*
	 * Check bounds, remove, music... Events for the playing mechanics.
	 * 
	*/
	
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
	void checkLifeBounds() {
		if (life != null && life.x <- extraLife.WIDTH) {
			life = null;
			
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
	long lastBullet = initialTime;

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
			long time = timeNow - lastBullet;
			
			if (time < 0 || time > 300) { //Max of 1 shot per second
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
		
		else if (key == KeyEvent.VK_U) { //ULTI ACTIVE
						
			if (activeUlti == true) { //Max of 1 shot per second
				
				ArrayList<Enemy> enemiesRemoved = new ArrayList<Enemy>();
				
				for (int i = 0; i < enemies.size(); i++) { //Kill all enemies
					enemiesRemoved.add(enemies.get(i));
						extra += 100;
				}
				
				for (int j = 0; j < enemiesRemoved.size(); j++) {
					removeEnemy(enemiesRemoved.get(j));
				}
				
				try {
					playSE(7);
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				activeUlti = false;
				Ship.ultiAnimation = true;
				enemiesKilled = 0;
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