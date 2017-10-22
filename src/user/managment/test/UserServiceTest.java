package user.managment.test;

import java.sql.SQLException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import user.managment.db.DBConnection;
import user.managment.db.DBConnectionProperty;
import user.managment.model.User;
import user.managment.service.ApplicationApi;

public class UserServiceTest extends JerseyTest {

	private static User user;
	private static DBConnection dbConnection;
	private static DBConnectionProperty dbConnectionProperty;

	@Override
	public Application configure() {
		return new ApplicationApi();
	}

	@BeforeClass
	public static void setup() {
		ApplicationApi.isTest = true;
		dbConnectionProperty = new DBConnectionProperty();
		dbConnectionProperty.setDriver("com.mysql.jdbc.Driver");// THE DRIVER - MYSQL IN THIS CASE
		dbConnectionProperty.setUrl("jdbc:mysql://localhost:3306/usermanagment_test");// DATABASE URL
		dbConnectionProperty.setUser("root");// DATABASE USER
		dbConnectionProperty.setPassword("pass");// DATABASE PASSWORD
		dbConnection = DBConnection.getDBConnection(dbConnectionProperty);
		try {
			dbConnection.getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			System.err.println("Error - endSetup - UserServiceTest :: " + e.getMessage());
		}
		user = new User();
	}

	@Test
	public void testUserService() {

		// SAVE USER - INSERT USER TEST
		user.setName("test");
		user.setEmail("test@test.com");
		user.setTempPass("123");

		Response resp = target("/user").request().put(Entity.entity(user, MediaType.APPLICATION_JSON));
		user = resp.readEntity(User.class);
		Assert.assertEquals(201, resp.getStatus());
		Assert.assertNotEquals(0, user.getUserId());
		// SAVE USER - INSERT USER END

		// SIGN IN - LOGIN TEST
		Form form = new Form();
		form.param("email", user.getEmail());
		form.param("pass", user.getTempPass());

		resp = target("/user/signIn").request().post(Entity.form(form));
		String token = resp.readEntity(String.class);
		Assert.assertEquals(200, resp.getStatus());
		Assert.assertNotEquals(null, token);
		// SIGN IN - LOGIN END
		
		// USER UPDATE - EDIT USER TEST
		user.setName("update name");
		resp = target("/user").request().header("Authorization", "Bearer " + token)
				.post(Entity.entity(user, MediaType.APPLICATION_JSON));
		Assert.assertEquals(200, resp.getStatus());
		// USER UPDATE - EDIT USER END

		// READ USER TEST
		resp = target("/user").request().header("Authorization", "Bearer " + token).get();
		Assert.assertEquals(200, resp.getStatus());
		// READ USER END

		// FORGOTTEN PASSWORD TEST
		resp = target("/user/forgottenPassword").request().post(Entity.entity(user.getEmail(), MediaType.TEXT_PLAIN));
		// FORGOTTEN PASSWORD END

		// USER DELETE TEST
		resp = target("/user/" + user.getUserId()).request().header("Authorization", "Bearer " + token).delete();
		Assert.assertEquals(204, resp.getStatus());
		// USER DELETE END
	}

	@AfterClass
	public static void endSetup() {
		try {
			dbConnection.getConnection().rollback();
		} catch (Exception e) {
			System.err.println("Error - endSetup - UserServiceTest :: " + e.getMessage());
		} finally {
			dbConnection.closeConnection();
		}
	}
}
