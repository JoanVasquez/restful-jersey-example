package user.managment.service.exception.mapper;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

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
		Error error = new Error(type.getStatusCode(), type.getReasonPhrase(), ex.getMessage());
		return Response.status(error.getStatus()).entity(error).build();

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
