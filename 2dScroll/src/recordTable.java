import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Record table implementing SQLite for storing as the name says
//records for each player and rankings


public class recordTable {
	
	String name;
	int points;
	
	recordTable(String name, int points){
		
		this.name = name;
		this.points = points;
		
	}

	public void updateTable () throws SQLException{
		 //TODO Auto-generated method stub
		Connection conn = DriverManager.getConnection("jdbc:sqlite:recordTable.sqlite"); //Open database
		Statement smt = conn.createStatement(); //to execute anthing first create statement
		smt.executeUpdate("INSERT INTO Records (Names, Points) VALUES ('" + name + "', " + points + ")");
		smt.close();
		conn.close();

		
	}
	
	public void printTable() throws SQLException{
		
		Connection conn = DriverManager.getConnection("jdbc:sqlite:recordTable.sqlite"); //Open database
		Statement smt = conn.createStatement(); //to execute anthing first create statement
		ResultSet rs = smt.executeQuery("SELECT Names FROM Records");
	
		while (rs.next()) {
		
			System.out.println(rs.getString("Names"));
		
		}
		
		smt.close();
		conn.close();
	}

}
