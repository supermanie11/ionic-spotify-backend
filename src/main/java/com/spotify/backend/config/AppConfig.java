package com.spotify.backend.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.spotify.backend.api.PlayListResource;
import com.spotify.backend.api.UserResource;
import com.spotify.backend.api.filters.AutenticatedRequest;
import com.spotify.backend.api.filters.AutenticatedResponse;
import com.spotify.backend.error.AuthorizationExceptionMapper;
import com.spotify.backend.error.BusinessExceptionMapper;
import com.spotify.backend.error.GenericExceptionMapper;
import com.spotify.backend.error.ValidationExceptionMapper;



@Configuration
@Component
@ApplicationPath("/api")
public class AppConfig extends ResourceConfig {
	
	public AppConfig() {
		register(AuthorizationExceptionMapper.class);
		register(BusinessExceptionMapper.class);
		register(GenericExceptionMapper.class);
		register(ValidationExceptionMapper.class);
		register(PlayListResource.class);
		register(UserResource.class);
		register(AutenticatedRequest.class);
		register(AutenticatedResponse.class);
	}

}
