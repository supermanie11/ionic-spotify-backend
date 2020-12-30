package com.spotify.backend.api;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spotify.backend.entities.PlayList;
import com.spotify.backend.service.PlayListService;

@Component
@Path("/users")
@PermitAll
public class UserResource {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	final int chunk_size = 1024 * 1024; // 1MB chunks

	@Autowired
	private PlayListService playListService;

	// @RolesAllowed({ "CLIENT" })

	@GET
	@Path("/me")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response top10() throws Exception {

		PlayList playList = playListService.listTop10();

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(" retrieving entities {}", playList.toString());
		}

		return Response.ok().entity(playList).build();
	}
}
