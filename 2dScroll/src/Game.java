
public class Game {
	
	Window w;
	int y;
	Game(Window w){
		this.w = w;
	}
	//Game logic
	void run() {
		//Initialize screen objects
		y = 50;
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
		y++;
	}
	void repaintScreen() { //Draw new line and repaint screen
		
		w.repaint(); 
	}
}
