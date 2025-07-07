package com.bookmakase.exception.recommend;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateRecommendationException extends RuntimeException {
	public DuplicateRecommendationException(String message) {
		super(message);
	}
}
