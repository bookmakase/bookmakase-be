package com.bookmakase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {
	public BookNotFoundException(Long bookId) {
		super("해당 ID의 도서를 찾을 수 없습니다. bookId=" + bookId);
	}
}