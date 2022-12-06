import java.awt.Color;

public class Game {
	
	Window w;
	//int y;
	Ship s[] = new Ship[3];
	Game(Window w){
		this.w = w;
	}
	
	//Game logic
	
	void run() {
		//Initialize screen objects
		//y = 50;
		s[0] = new Ship(50,50,1);
		s[1] = new Ship(50,150,2);
		s[2] = new Ship(70,250,3);
		
		while(true) {
			
			moveObjects(); //Move screen objects 
			//Detect collisions
			repaintScreen();//Repaint screen
			
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
		for (int i = 0; i < s.length; i++) {
			s[i].move();
		}
	}
	
	void repaintScreen() { //Draw new line and repaint screen
		w.gr.setColor(Color.WHITE);
		w.gr.fillRect(0, 0, w.WIDTH, w.HEIGHT);
		//w.gr.setColor(Color.BLUE);
		//w.gr.drawLine(50, 50, 700, y);
		for (int i = 0; i < s.length; i++) {
			s[i].paint(w.gr);
		}

		w.repaint(); 
	}
}
