import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Window extends Frame implements WindowListener{
	
	Menu menu;
	Image im;
	Graphics gr;

	static int HEIGHT = 800;
	static int WIDTH = 800;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Window();
	}
	
	Window(){ //Main window game
		super("Game");
		setSize(WIDTH, HEIGHT);
		setVisible(true); 
		addWindowListener(this);
		im = this.createImage(WIDTH, HEIGHT);
		gr = im.getGraphics();
		menu = new Menu(this);
		menu.start();
		
	}
	
	public void paint(Graphics gr) {
		gr.drawImage(im, 0, 0, null);
	}
	
	public void update(Graphics gr) {
		paint(gr);
	}
	
	
	
	
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		System.exit(0);
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
