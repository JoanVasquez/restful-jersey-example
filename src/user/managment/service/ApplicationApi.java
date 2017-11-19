package user.managment.service;

import javax.ws.rs.ApplicationPath;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import user.managment.db.dao.UserDao;
import user.managment.filter.JWTTokenNeededFilter;
import user.managment.filter.ResponseFilter;

/**
 * @author JoanVasquez
 * Date 19/10/2017
 * A class to configure the web service resource
 */
@ApplicationPath("/webapi")
public class ApplicationApi extends ResourceConfig {
	
	/**
	 * Boolean to check whether was ran from a JUnit context or not
	 */
	public static boolean isTest = false;

	/**
	 * The constructor method
	 */
	public ApplicationApi() {
		packages("user.managment.service", "user.managment.filter");
		register(UserService.class);
		register(JWTTokenNeededFilter.class);
		register(ResponseFilter.class);
		register(new AbstractBinder() {
			
			@Override
			protected void configure() {
				bind(UserDao.class).to(UserDao.class);
			}
		});
	}
}
