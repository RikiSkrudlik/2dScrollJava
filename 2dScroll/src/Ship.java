import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ship {
	
	int x,y, vx, vy, counter = 1, i;
	static Boolean ultiAnimation = false;
	static int speedx = 8, speedy = 6;
	int WIDTH = 150, HEIGHT = 90, turboWidth = 60, turboHeight = 40;
	Image[] ulti = new Image[11];
	Image turbo1, turbo2, turbo3, turbo4;  //Images for the turbo/exhaust animation
	
	static Image img;

	Ship(int x, int y) {
		this.x = x;
		this.y = y;
		
	}
	
	void move() {
		if (x < 15 && vx < 0) { //Left side collision
			return;
		}
		if (x > Game.window.WIDTH - WIDTH && vx > 0) {
			return;
		}
		if (y < 15 && vy < 0) {
			return; 
		}
		if (y > Game.window.HEIGHT - HEIGHT && vy > 0) {
			return;
		}
		x += vx;
		y += vy;
		
	}
	
	void initTurbo() throws IOException {
		
		turbo1 = ImageIO.read(new File("res/exhaust1.png"));
		turbo2 = ImageIO.read(new File("res/exhaust2.png"));
		turbo3 = ImageIO.read(new File("res/exhaust3.png"));
		turbo4 = ImageIO.read(new File("res/exhaust4.png"));
		
		ulti[0] = ImageIO.read(new File("res/explosion1_1.png"));
		ulti[1] = ImageIO.read(new File("res/explosion1_2.png"));
		ulti[2] = ImageIO.read(new File("res/explosion1_3.png"));
		ulti[3] = ImageIO.read(new File("res/explosion1_4.png"));
		ulti[4] = ImageIO.read(new File("res/explosion1_5.png"));
		ulti[5] = ImageIO.read(new File("res/explosion1_6.png"));
		ulti[6] = ImageIO.read(new File("res/explosion1_7.png"));
		ulti[7] = ImageIO.read(new File("res/explosion1_8.png"));
		ulti[8] = ImageIO.read(new File("res/explosion1_9.png"));
		ulti[9] = ImageIO.read(new File("res/explosion1_10.png"));
		ulti[10] = ImageIO.read(new File("res/explosion1_11.png"));


		
	}
	
	void paint(Graphics gr) {
		
		gr.drawImage(img, x, y, WIDTH, HEIGHT, null);
		
		if (counter%8 == 1) { //for the exhaust animation
			gr.drawImage(turbo1, x - 45, y + 22, turboWidth, turboHeight, null);
		}
		else if (counter%8 == 3) { //for the exhaust animation
			gr.drawImage(turbo2, x - 45, y + 22, turboWidth, turboHeight, null);
		}
		else if (counter%8 == 5) { //for the exhaust animation
			gr.drawImage(turbo3, x - 45, y + 22, turboWidth, turboHeight, null);
		}
		else if (counter%8 == 7) { //for the exhaust animation
			gr.drawImage(turbo4, x - 45, y + 22, turboWidth, turboHeight, null);
		}
		//gr.setColor(Color.ORANGE);

		//gr.drawRect(x, y, WIDTH-30, HEIGHT -35);
		
		getBounds();
		
		if (ultiAnimation) {
			gr.drawImage(ulti[i], -200, -200, 1000, 1000, null);
			i++;
		}
		
		if (i == 10) {
			ultiAnimation = false;
			i = 0;
		}
		counter++;

	}
	
	void paintUlti(Graphics gr) {
		
	}
	
	void checkBorder() {//To check if ship touches border
		if (x == Game.window.WIDTH) {
			vx = 0;
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, WIDTH - 30, HEIGHT - 35);
	}
	
	
}
