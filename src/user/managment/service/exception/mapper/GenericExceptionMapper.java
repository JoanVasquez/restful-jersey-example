package user.managment.service.exception.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import jersey.repackaged.com.google.common.collect.Lists;
import user.managment.model.Error;

/**
 * @author JoanVasquez
 * Date 19/10/2017
 * A class to generate All Kind of Exception in JSON format
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

	/* (non-Javadoc)
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse(Exception ex) {
		Response.StatusType type = getStatusType(ex);
		List<Error> errors = new ArrayList<Error>();
		Error error = new Error(type.getStatusCode(), ex.getMessage(), "");
		errors.add(error);
		GenericEntity<List<Error>> entityError = new GenericEntity<List<Error>>(Lists.newArrayList(errors)) {};
		return Response.status(type.getStatusCode()).entity(entityError).build();

	}
	
	/**
	 * @param ex - Throwable
	 * @return - HTTP Status to generate the exception Response
	 */
	private Response.StatusType getStatusType(Throwable ex) {
		if (ex instanceof WebApplicationException) {
			return ((WebApplicationException) ex).getResponse().getStatusInfo();
		} else {
			return Response.Status.INTERNAL_SERVER_ERROR;
		}
	}
}
