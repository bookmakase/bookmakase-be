package com.bookmakase.config;

public interface Whitelist {
	String[] PATHS = {
		"/api/v1/auth/login",
		"/api/v1/auth/register",
		"/api/v1/auth/refreshtoken",
		// "/api/v1/books/**",
		"/swagger-ui/**",
		"/v3/api-docs/**",
		"/swagger-resources/**",
		"/webjars/**",
		"/swagger-ui.html",
		"/error",
	};
}
