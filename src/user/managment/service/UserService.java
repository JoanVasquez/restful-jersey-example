package user.managment.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
		List<User> users = userDao.getEntities();
		//GenericEntity<List<User>> entity = new GenericEntity<List<User>>(Lists.newArrayList(users)) {};
		return Response.status(Response.Status.OK).entity(users).build();
	}

	@PUT
	public Response saveUser(@Valid @NotNull User user) throws Exception, ValidationException {
		user.setPass(Aes256.encryption(user.getTempPass()));
		user = userDao.saveEntity(user);
		return Response.status(Response.Status.CREATED).entity(user).build();
	}

	@POST
	@JWTTokenNeeded
	public Response updateUser(@Valid @NotNull User user) throws Exception, ValidationException {
		user.setPass(Aes256.encryption(user.getTempPass()));
		userDao.updateEntity(user);
		return Response.status(Response.Status.OK).build();
	}

	@DELETE
	@Path("/{userId}")
	@Consumes(MediaType.TEXT_PLAIN)
	@JWTTokenNeeded
	public Response deleteUser(@PathParam("userId") @NotNull int userId) throws Exception {
		userDao.deleteEntity(userId);
		return Response.status(Response.Status.OK).build();
	}

	@POST
	@Path("/signIn")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response signIn(@FormParam("email") String email, @FormParam("pass") String password) throws Exception {
		Response resp;
		byte[] pass = Aes256.encryption(password);
		User user = userDao.signIn(email, pass);
		if(user != null) {
			Map<String, String> datas = new HashMap<String, String>();
			datas.put("token", issueToken(String.valueOf(user.getUserId())));
			datas.put("userId",String.valueOf(user.getUserId()));

			resp =  Response.status(Response.Status.OK).entity(datas).build();
		}
		else {
			System.out.println("Not Found Any User");
			resp =  Response.status(Response.Status.NOT_FOUND).entity("User Not Found").build();
		}
		return resp;
	}

	@POST
	@Path("/forgottenPassword")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response forgottenPassword(@NotNull String email) throws Exception {
		String pass = null;
		pass = userDao.getPassword(email);
		SendForgottenPassword.sendEmail(email, pass);
		return Response.status(Response.Status.OK).build();
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
