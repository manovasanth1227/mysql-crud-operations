package connectionMySQL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
	private static final String DRIVERNAME = "com.mysql.jdbc.Driver";
	static final private String URL = "jdbc:mysql://localhost:3306/test";
	static final private String USERID = "root";
	static final private String PASSWORD = "root";
	
	public static Connection establishMySQLConnection() throws ClassNotFoundException, SQLException {
			Class.forName(DRIVERNAME);
			Connection conn = DriverManager.getConnection(URL,USERID,PASSWORD);
			return conn;
	}
}
