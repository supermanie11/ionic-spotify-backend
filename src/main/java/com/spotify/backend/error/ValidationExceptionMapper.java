package com.spotify.backend.error;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.spotify.backend.error.dto.ValidationErrorDTO;
import com.spotify.backend.error.dto.ValidationsErrorDTO;
import com.spotify.backend.exception.ValidationException;


@Component
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Override
	public Response toResponse(ValidationException exception) {

		if (LOGGER.isErrorEnabled()) {
			LOGGER.error(exception.toString());
		}

		ValidationsErrorDTO entity = new ValidationsErrorDTO(exception.getDetails(), exception.getStatusCode(), false);

		for (ConstraintViolation constraint : exception.getErrors()) {

			entity.getErrors().add(new ValidationErrorDTO( (String)constraint.getPropertyPath().toString(), constraint.getMessage()));
		}

		return Response.status(exception.getStatusCode()).entity(entity).type(MediaType.APPLICATION_JSON).build();
	}

}
