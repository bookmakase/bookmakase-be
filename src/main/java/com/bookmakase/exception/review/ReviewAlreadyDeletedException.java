package com.bookmakase.exception.review;

public class ReviewAlreadyDeletedException extends RuntimeException {
	public ReviewAlreadyDeletedException(String message) {
		super(message);
	}
}
