package user.managment.db;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Date 19/10/2017 A class to connect to the Database
 * 
 * @author JoanVasquez
 * 
 */
public class DBConnection {

	private static DBConnection dbConnection; // VAR OF THIS CLASS
	private Connection connection; // VAR OF CONNECTION CLASS - TO CREATE THE DATABASE CONNECTION
	private BasicDataSource basicDataSource;

	/**
	 * The Constructor of this class is private to use SINGLETON Pattern
	 * 
	 * @param dbConnectionProperties
	 *            - Property of the connection
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private DBConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		if (this.basicDataSource == null) {
			this.basicDataSource = new BasicDataSource();
			this.basicDataSource.setUrl("jdbc:mysql://localhost:3306/usermanagment");
			this.basicDataSource.setUsername("root");
			this.basicDataSource.setPassword("pass");
			this.basicDataSource.setMinIdle(5);
			this.basicDataSource.setMaxIdle(10);
			this.basicDataSource.setMaxOpenPreparedStatements(100);
		}
		this.connection = this.basicDataSource.getConnection();
		System.out.println("Connection established...");
	}

	/**
	 * @param dbConnectionProperties
	 * @return an instance of this class - singleton pattern
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static DBConnection getDBConnection() throws SQLException, ClassNotFoundException {
		try {
			if (DBConnection.dbConnection == null) {
				DBConnection.dbConnection = new DBConnection();
			} else if (dbConnection.getConnection().isClosed()) {
				DBConnection.dbConnection = new DBConnection();
			}
		} catch (Exception e) {
			System.err.println("Error --> " + e.getMessage());
		}

		return dbConnection;
	}

	/**
	 * @return an instance of the class Connection to the access to the Database
	 */
	public Connection getConnection() {
		return this.connection;
	}

}