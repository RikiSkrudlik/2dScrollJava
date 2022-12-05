import java.awt.Frame;
import java.awt.Graphics;

public class Window extends Frame{
	Graphics gr; //Create global var g
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Window();
	}
	
	Window(){ //Main window game
		super("Game");
		setSize(800, 800);
		setVisible(true);
		Game g = new Game(this);
		g.run();
	}
	
	public void paint(Graphics gr) {
		this.gr = gr;
		if (this.gr != null) {
			gr.drawLine(50, 50, 700, g.y);
		}
	}

}
