package greencinema.DBPKG;

import java.sql.*;

public class Util {

	public static Connection getConnection() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection con = DriverManager.getConnection
				("jdbc:mysql://localhost:3306/greencinema?serverTimezone=UTC&useSSL=false"
				, "root"
				, "1q2w3e4r");
		
		return con;		
	}
	
}