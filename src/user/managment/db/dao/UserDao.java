package user.managment.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import user.managment.crud.CrudMethod;
import user.managment.db.DBConnection;
import user.managment.db.DBConnectionProperty;
import user.managment.db.query.Query;
import user.managment.model.User;
import user.managment.security.Aes256;
import user.managment.service.ApplicationApi;

/**
 * @author JoanVasquez A class to manipulate USER DAO or process it to the
 *         Database Date 19/10/2017
 */
public class UserDao extends Query implements CrudMethod<User> {

	private DBConnection dbConnection;
	private PreparedStatement prepareStatement;
	private ResultSet resultSet;
	private DBConnectionProperty dbConnectionProperty; // DATABASE PROPERTIES

	/**
	 * Constructor method - Connecting to the Database
	 */
	public UserDao() {
		if (ApplicationApi.isTest) {
			dbConnectionProperty = new DBConnectionProperty();
			dbConnectionProperty.setDriver("com.mysql.jdbc.Driver");// THE DRIVER - MYSQL IN THIS CASE
			dbConnectionProperty.setUrl("jdbc:mysql://localhost:3306/usermanagment_test");// DATABASE URL
			dbConnectionProperty.setUser("root");// DATABASE USER
			dbConnectionProperty.setPassword("pass");// DATABASE PASSWORD
			dbConnection = DBConnection.getDBConnection(dbConnectionProperty);
		} else {
			dbConnectionProperty = new DBConnectionProperty();
			dbConnectionProperty.setDriver("com.mysql.jdbc.Driver");// THE DRIVER - MYSQL IN THIS CASE
			dbConnectionProperty.setUrl("jdbc:mysql://localhost:3306/usermanagment");// DATABASE URL
			dbConnectionProperty.setUser("root");// DATABASE USER
			dbConnectionProperty.setPassword("pass");// DATABASE PASSWORD
			dbConnection = DBConnection.getDBConnection(dbConnectionProperty);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see user.managment.crud.CrudMethod#saveEntity(java.lang.Object)
	 */
	@Override
	public User saveEntity(User entity) throws SQLException {
		int userId = 0;
		prepareStatement = dbConnection.getConnection().prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
		prepareStatement.setString(1, entity.getName());
		prepareStatement.setString(2, entity.getEmail());
		prepareStatement.setBytes(3, entity.getPass());

		prepareStatement.executeUpdate();
		resultSet = prepareStatement.getGeneratedKeys();
		if (resultSet.next()) {
			userId = resultSet.getInt(1);
			entity.setUserId(userId);
		}

		resultSet.close();
		prepareStatement.close();

		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see user.managment.crud.CrudMethod#updateEntity(java.lang.Object)
	 */
	@Override
	public void updateEntity(User entity) throws SQLException {
		prepareStatement = dbConnection.getConnection().prepareStatement(UPDATE_USER);
		prepareStatement.setString(1, entity.getName());
		prepareStatement.setBytes(2, entity.getPass());
		prepareStatement.setInt(3, entity.getUserId());
		prepareStatement.executeUpdate();

		prepareStatement.close();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see user.managment.crud.CrudMethod#deleteEntity(int)
	 */
	@Override
	public void deleteEntity(int id) throws SQLException {
		prepareStatement = dbConnection.getConnection().prepareStatement(DELETE_USER);
		prepareStatement.setInt(1, id);
		prepareStatement.executeUpdate();

		prepareStatement.close();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see user.managment.crud.CrudMethod#getEntities()
	 */
	@Override
	public List<User> getEntities() throws SQLException {
		List<User> listUser = new ArrayList<User>();
		prepareStatement = dbConnection.getConnection().prepareStatement(READ_USERS);
		resultSet = prepareStatement.executeQuery();

		while (resultSet.next()) {
			User user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
					Aes256.decryption(new String(resultSet.getBytes(4))));
			listUser.add(user);
		}

		resultSet.close();
		prepareStatement.close();

		return listUser;
	}

	/**
	 * @param email
	 * @param pass
	 * @return - {@link user.managment.model.User}
	 * @throws SQLException
	 */
	public User signIn(String email, byte[] pass) throws SQLException {
		prepareStatement = dbConnection.getConnection().prepareStatement(SIGN_IN);
		prepareStatement.setString(1, email);
		prepareStatement.setBytes(2, pass);

		User user = null;

		resultSet = prepareStatement.executeQuery();
		if (resultSet.next()) {
			user = new User();
			user.setUserId(resultSet.getInt(1));
			user.setName(resultSet.getString(2));
			user.setEmail(resultSet.getString(3));
			user.setPass(resultSet.getBytes(4));
		}

		resultSet.close();
		prepareStatement.close();
		return user;
	}

	/**
	 * @param email
	 * @return - String - Password
	 * @throws SQLException
	 */
	public String getPassword(String email) throws SQLException {
		String pass = null;
		prepareStatement = dbConnection.getConnection().prepareStatement(FORGOTTEN_PASSWORD);
		prepareStatement.setString(1, email);

		resultSet = prepareStatement.executeQuery();
		if (resultSet.next())
			pass = Aes256.decryption(resultSet.getString(1));
		resultSet.close();
		prepareStatement.close();

		return pass;
	}

	/**
	 * Closing Database connection
	 */
	public void closeConnection() {
		dbConnection.closeConnection();
	}

}
