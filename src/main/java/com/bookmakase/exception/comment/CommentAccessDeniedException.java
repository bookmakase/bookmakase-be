package com.bookmakase.exception.comment;

public class CommentAccessDeniedException extends RuntimeException {
	public CommentAccessDeniedException(String message) {
		super(message);
	}
}
