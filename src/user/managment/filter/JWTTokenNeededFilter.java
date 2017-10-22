package user.managment.filter;

import java.io.IOException;
import java.security.Key;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import io.jsonwebtoken.Jwts;
import user.managment.model.Error;
import user.managment.security.Sha256;

/**
 * @author JoanVasquez
 * Date 19/10/2017
 * A class to setup the authentication
 */
@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {

	/* (non-Javadoc)
	 * @see javax.ws.rs.container.ContainerRequestFilter#filter(javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		Error error = new Error(401, "There is a problem with your token.", "");
		Response resp = Response.status(Response.Status.UNAUTHORIZED).entity(error).build();
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			requestContext.abortWith(resp);
		} else {
			try {
				String token = authorizationHeader.substring("Bearer".length()).trim();
				Key key = Sha256.generateKey();
				Jwts.parser().setSigningKey(key).parseClaimsJws(token);
			} catch (Exception e) {
				requestContext.abortWith(resp);
			}

		}
	}
}
