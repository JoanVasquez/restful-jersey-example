package user.managment.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Date 19/10/2017
 * A class to connect to the Database
 * @author JoanVasquez
 * 
 */
public class DBConnection {
	
	private static DBConnection dbConnection; // VAR OF THIS CLASS
	private static Connection connection; // VAR OF CONNECTION CLASS - TO CREATE THE DATABASE CONNECTION

	/**
	 * The Constructor of this class is private to use SINGLETON Pattern
	 * @param dbConnectionProperties - Property of the connection
	 */
	private DBConnection(DBConnectionProperty dbConnectionProperties) {
		try {
			Class.forName(dbConnectionProperties.getDriver()); // LOAD THE MYSQL DRIVER
			connection = DriverManager.getConnection(dbConnectionProperties.getUrl(), dbConnectionProperties.getUser(),
					dbConnectionProperties.getPassword()); // CREATE THE DATABASE CONNECTION

		} catch (SQLException ex) {
			System.err.println("Connection to the DataBase failed " + ex.getMessage());
		} catch (ClassNotFoundException e) {
			System.err.println("Loading driver failed: " + e.getMessage());
		}
	}

	/**
	 * @param dbConnectionProperties
	 * @return an instance of this class - singleton pattern
	 */
	public synchronized static DBConnection getDBConnection(DBConnectionProperty dbConnectionProperties) {
		if (dbConnection == null) {
			System.out.println("New connection...");
			dbConnection = new DBConnection(dbConnectionProperties);
		}
		return dbConnection;
	}
	
	/**
	 * @return an instance of the class Connection to the access to the Database
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Close the Database connection
	 */
	public void closeConnection() {

		if ((connection != null) && (dbConnection != null)) {
			try {
				connection.close();
				dbConnection = null;
			} catch (SQLException e) {
				System.err.println("Failed to close the connection " + e.getMessage());
			}
		} else {
			System.out.println("Not opened connection");
		}

	}
}
