package com.bookmakase.exception.auth;

public class UnauthorizedException extends RuntimeException {
	public UnauthorizedException(String message) {
		super(message);
	}
}
