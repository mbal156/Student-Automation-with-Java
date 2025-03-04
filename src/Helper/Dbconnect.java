package Helper;
import java.sql.*;

public class Dbconnect {

	private static Connection c;
	
	public Dbconnect() {}
	public Connection connDb() {
		try {
			this.c=DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
			return c;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return c;
	}
	
	
	
}
