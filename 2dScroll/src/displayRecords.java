import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;

public class displayRecords extends Thread implements KeyListener{
	
	Window w;
	Graphics gr;
	Boolean active = true;
	
	displayRecords(Window w){
	
		this.w = w;
		this.gr = w.gr;
		w.addKeyListener(this);
	}
	
	public void run() {
		
		initImages();
		initFont();
		
		while(active) {
			
		}
		
	}
	
	public void initImages() {
		
		try {

			storyScreen.img = ImageIO.read(new File("res/BackgroundBlurr.png"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void initFont() {
		
		Font font;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("font/arcade.ttf"));
			font = font.deriveFont(Font.PLAIN, 26);
			gr.setFont(font);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void paintScreen() throws SQLException{
		
		Connection conn = DriverManager.getConnection("jdbc:sqlite:recordTable.sqlite"); //Open database
		Statement smt = conn.createStatement(); //to execute anthing first create statement
		ResultSet rs = smt.executeQuery("SELECT * FROM Records ORDER BY Points DESC");
		
		gr.drawString("Players                                Score", 80, 80);
		
		for (int i = 0; i < 10; i++) { //Print the first 10 elements in descending order if possible
		
			try {
				gr.drawString(rs.getString("Names") + "                   " + rs.getLong("Points"), 80, 80 + i*50);
			} catch (SQLException e) { 
				// TODO Auto-generated catch block
				break;
			}
			
		
		}
		
		smt.close();
		conn.close();
	}
	
	
	
	
	
	
	
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ENTER) {
			try {
				active = false;
				w.removeKeyListener(this);
				
			} 
			catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
