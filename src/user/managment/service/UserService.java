package user.managment.service;

import java.security.Key;
import java.util.Date;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jersey.repackaged.com.google.common.collect.Lists;
import user.managment.db.dao.UserDao;
import user.managment.email.SendForgottenPassword;
import user.managment.filter.JWTTokenNeeded;
import user.managment.model.User;
import user.managment.security.Aes256;
import user.managment.security.Sha256;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserService {

	@Context
	private UserDao userDao;
	@Context
	private UriInfo uriInfo;

	@GET
	@JWTTokenNeeded
	public Response readUsers() throws Exception {
		Response resp = null;
		List<User> users = userDao.getEntities();
		GenericEntity<List<User>> entity = new GenericEntity<List<User>>(Lists.newArrayList(users)) {
		};
		resp = Response.status(Response.Status.OK).entity(entity).build();

		return resp;
	}

	@PUT
	public Response saveUser(@Valid @NotNull User user) throws Exception, ValidationException {
		Response resp = null;
		user.setPass(Aes256.encryption(user.getTempPass()));
		user = userDao.saveEntity(user);
		resp = Response.status(Response.Status.CREATED).entity(user).build();
		return resp;
	}

	@POST
	@JWTTokenNeeded
	public Response updateUser(@Valid @NotNull User user) throws Exception, ValidationException {
		Response resp = null;
		user.setPass(Aes256.encryption(user.getTempPass()));
		userDao.updateEntity(user);
		resp = Response.status(Response.Status.OK).build();
		return resp;
	}

	@DELETE
	@Path("/{userId}")
	@Consumes(MediaType.TEXT_PLAIN)
	@JWTTokenNeeded
	public Response deleteUser(@PathParam("userId") @NotNull int userId) throws Exception {
		Response resp = null;
		userDao.deleteEntity(userId);
		resp = Response.status(Response.Status.OK).entity(issueToken(String.valueOf(userId))).build();
		return resp;
	}

	@POST
	@Path("/signIn")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response signIn(@FormParam("email") String email, @FormParam("pass") String password) throws Exception {
		Response resp = null;
		byte[] pass = Aes256.encryption(password);
		User user = userDao.signIn(email, pass);
		if (user != null)
			resp = Response.status(Response.Status.OK).entity(issueToken(String.valueOf(user.getUserId()))).build();
		else
			resp = Response.status(Response.Status.NOT_FOUND).entity("Not user found").build();

		return resp;
	}

	@POST
	@Path("/forgottenPassword")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response forgottenPassword(@NotNull String email) throws Exception {
		Response resp = null;
		String pass = null;
		pass = userDao.getPassword(email);
		resp = Response.status(Response.Status.OK).build();
		SendForgottenPassword.sendEmail(email, pass);

		return resp;
	}

	@PreDestroy
	public void onDestroy() {
		if (!ApplicationApi.isTest)
			userDao.closeConnection();
	}

	private String issueToken(String userId) {
		Key key = Sha256.generateKey();
		return Jwts.builder().setSubject(userId).setIssuer(uriInfo.getAbsolutePath().toString()).setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS512, key).compact();
	}
}
