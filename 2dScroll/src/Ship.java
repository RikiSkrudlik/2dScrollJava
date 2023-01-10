import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ship {
	
	int x,y;
	int vx;
	int vy;
	int counter = 1;
	static int speedx = 8;
	static int speedy = 6;
	int WIDTH = 150;
	int HEIGHT = 90;
	BufferedImage turbo1, turbo2, turbo3, turbo4;  //Images for the turbo/exhaust animation
	
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
		//turbo1 = (BufferedImage) turbo1.getScaledInstance(40, 20, Image.SCALE_SMOOTH);
		turbo2 = ImageIO.read(new File("res/exhaust2.png"));
		turbo3 = ImageIO.read(new File("res/exhaust3.png"));
		turbo4 = ImageIO.read(new File("res/exhaust4.png"));
		
	}
	
	void paint(Graphics gr) {
		
		gr.drawImage(img, x, y, WIDTH, HEIGHT, null);
		if (counter%8 == 1) { //for the exhaust animation
			gr.drawImage(turbo1, x - 3*turbo1.getWidth(), y, WIDTH, HEIGHT, null);

		}
		else if (counter%8 == 3) { //for the exhaust animation
			gr.drawImage(turbo2, x - 3*turbo1.getWidth(), y, WIDTH, HEIGHT, null);
		}
		else if (counter%8 == 5) { //for the exhaust animation
			gr.drawImage(turbo3, x - 3*turbo1.getWidth(), y, WIDTH, HEIGHT, null);
		}
		else if (counter%8 == 7) { //for the exhaust animation
			gr.drawImage(turbo4, x - 3*turbo1.getWidth(), y, WIDTH, HEIGHT, null);
		}
		//gr.setColor(Color.ORANGE);

		//gr.drawRect(x, y, WIDTH-30, HEIGHT -35);
		getBounds();
		counter ++;

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
