package com.bookmakase.exception.review;

public class ReviewNotFoundException extends RuntimeException {
	public ReviewNotFoundException(String message) {
		super(message);
	}
}
