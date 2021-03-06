package user.managment.service.exception.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import user.managment.model.Error;

/**
 * @author JoanVasquez
 * Date 19/10/2017
 * A class to generate validation exceptions
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
	
	/* (non-Javadoc)
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse(ConstraintViolationException cve) {
		return Response.status(Response.Status.BAD_REQUEST).entity(setError(cve)).build();
	}

	/**
	 * @param cve - ConstraintViolationException
	 * @return List of Error class
	 */
	private List<Error> setError(ConstraintViolationException cve) {
		List<Error> errors = new ArrayList<Error>();
		for (ConstraintViolation<?> cv : cve.getConstraintViolations()) {
			Error error = new Error(400, cv.getMessage(), "");
			errors.add(error);
		}

		return errors;
	}

}
