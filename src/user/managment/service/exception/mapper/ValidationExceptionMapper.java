package user.managment.service.exception.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Produces;
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
		GenericEntity<List<Error>> entityError = new GenericEntity<List<Error>>(Lists.newArrayList(setError(cve))) {};
		return Response.status(Response.Status.BAD_REQUEST).entity(entityError).build();
	}

	/**
	 * @param cve - ConstraintViolationException
	 * @return List of Error class
	 */
	private List<Error> setError(ConstraintViolationException cve) {
		List<Error> errors = new ArrayList<Error>();
		for (ConstraintViolation<?> cv : cve.getConstraintViolations()) {
			Error error = new Error(400, cv.getPropertyPath().toString(), cv.getMessage());
			errors.add(error);
		}

		return errors;
	}

}
