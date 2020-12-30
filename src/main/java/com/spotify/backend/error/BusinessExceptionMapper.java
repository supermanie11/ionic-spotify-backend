package com.spotify.backend.error;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.spotify.backend.error.dto.ResponseDTO;
import com.spotify.backend.exception.BusinessException;

@Component
@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Override
	public Response toResponse(BusinessException exception) {
		
		if (LOGGER.isErrorEnabled()) {
			LOGGER.error(exception.toString());
		}
		
		ResponseDTO entity = new ResponseDTO(exception.getDetails(), exception.getStatusCode(), false);
		
		return Response
					.status(exception.getStatusCode())
						.entity(entity)
							.type(MediaType.APPLICATION_JSON)
								.build();
	}
	
}