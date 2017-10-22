package user.managment.db.query;

/**
 * @author JoanVasquez
 * Date 19/10/2017
 * A class with all the Database query
 */
public abstract class Query {
	
	// READ USERS
	protected final static String READ_USERS = "SELECT * FROM Users ORDER BY Users.userId DESC";

	// INSERT USER
	protected final static String INSERT_USER = "INSERT INTO Users(name, email, password) VALUES(?, ?, ?)";

	// UPDATE USER
	protected final static String UPDATE_USER = "UPDATE Users SET Users.name = ?, Users.password = ? WHERE Users.userId = ?";

	// DELETE USER
	protected final static String DELETE_USER = "DELETE FROM Users WHERE Users.userId = ?";

	// SIGN IN
	protected final static String SIGN_IN = "SELECT * FROM Users WHERE Users.email = ? AND Users.password = ?";

	// FORGOTTEN PASSWORD
	protected final static String FORGOTTEN_PASSWORD = "SELECT Users.password FROM Users WHERE Users.email = ?";

}
