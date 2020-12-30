package com.spotify.backend.api.filters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.security.PermitAll;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.message.internal.ReaderWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spotify.backend.exception.AuthorizationException;


public class AutenticatedRequest implements ContainerRequestFilter {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Context
	private ResourceInfo resourceInfo;


	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		if (requestContext.getMethod().equals("POST") || requestContext.getMethod().equals("PUT")) {

			String message = (requestContext.getEntityStream() == null ? "empty request"
					: retrieveBodyRequest(requestContext));

		}
		
		if (resourceInfo.getResourceClass().isAnnotationPresent(PermitAll.class)) {
			return;
		}

		if (resourceInfo.getResourceMethod().isAnnotationPresent(PermitAll.class)) {
			return;
		}

		if (requestContext.getMethod().equals("OPTIONS")) {

			return;
		}

		String bearerToken = requestContext.getHeaderString("Authorization");

		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			bearerToken = bearerToken.substring(7);

			try {
				
				
			} catch (Exception ex) {

				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("invalid request - {}", ex.getMessage());
				}

				throw new AuthorizationException(401, 001, ex.getMessage());
			}

		} else {
			throw new AuthorizationException(401, 001, "token de acceso invalido");
		}
	}

	private String retrieveBodyRequest(ContainerRequestContext containerRequest) throws IOException {
		String body = "data - ";

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream in = containerRequest.getEntityStream();
		final StringBuilder b = new StringBuilder();
		try {
			if (in.available() > 0) {
				ReaderWriter.writeTo(in, out);

				byte[] requestEntity = out.toByteArray();

				if (requestEntity.length == 0)
					return body;

				b.append(new String(requestEntity));

				containerRequest.setEntityStream(new ByteArrayInputStream(requestEntity));

				return body + b.toString();
			}
		} catch (IOException ex) {
			return body;
		}

		return body;
	}

}