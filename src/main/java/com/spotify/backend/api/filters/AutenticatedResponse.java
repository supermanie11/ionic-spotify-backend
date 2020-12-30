package com.spotify.backend.api.filters;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutenticatedResponse implements ContainerResponseFilter {

	private static Logger LOG = LoggerFactory.getLogger(AutenticatedResponse.class);

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {

		if (requestContext.getMethod().equals("POST") || requestContext.getMethod().equals("PUT")) {

			String message = (responseContext.getEntity() == null ? "empty response"
					: responseContext.getEntity().toString());

		}

		responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
		responseContext.getHeaders().add("Access-Control-Allow-Headers",
				"X-Requested-With, X-PINGOTHER, Content-Type, Origin, Accept, data-type, local-date, content-type, authorization");

		if (requestContext.getMethod().equals("OPTIONS")) {

			responseContext.setStatus(HttpServletResponse.SC_ACCEPTED);
		}

	}

}