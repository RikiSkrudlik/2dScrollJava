import java.awt.Graphics;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Record table implementing SQLite for storing as the name says
//records for each player and rankings


public class recordTable {
	
	String name;
	long points;
	
	recordTable(String name, long points){
		
		this.name = name;
		this.points = points;

		
	}

	public void updateTable () throws SQLException{
		 //TODO Auto-generated method stub
		Connection conn = DriverManager.getConnection("jdbc:sqlite:recordTable.sqlite"); //Open database
		Statement smt = conn.createStatement(); //to execute anthing first create statement
		smt.executeUpdate("INSERT INTO Records (Names, Points) VALUES ('" + name + "', " + points + ")");
		//smt.executeUpdate("DELETE FROM Records");
		smt.close();
		conn.close();

		
	}

}
