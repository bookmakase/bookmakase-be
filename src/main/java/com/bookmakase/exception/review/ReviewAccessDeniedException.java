package com.bookmakase.exception.review;

public class ReviewAccessDeniedException extends RuntimeException {
	public ReviewAccessDeniedException(String message) {
		super(message);
	}
}
