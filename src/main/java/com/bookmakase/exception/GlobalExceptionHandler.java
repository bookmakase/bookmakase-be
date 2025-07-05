package com.bookmakase.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bookmakase.exception.auth.DuplicateEmailException;
import com.bookmakase.exception.auth.UnauthorizedException;
import com.bookmakase.exception.book.BookNotFoundException;
import com.bookmakase.exception.review.ReviewAccessDeniedException;
import com.bookmakase.exception.review.ReviewAlreadyDeletedException;
import com.bookmakase.exception.review.ReviewNotFoundException;
import com.bookmakase.exception.user.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	/**
	 * 모든 예외 처리 (서버 내부 오류 포함)
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("status", 500);
		body.put("message", "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");

		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(body);
	}

	// 인증 파트
	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<Map<String, Object>> handleDuplicateEmailException(DuplicateEmailException ex) {
		Map<String, Object> body = new LinkedHashMap<>();

		body.put("message", ex.getMessage());
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.CONFLICT.value());
		body.put("error", "이메일 중복 오류");

		return new ResponseEntity<>(body, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<Map<String, Object>> handleUnauthorizedException(UnauthorizedException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("status", 401);
		body.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
	}

	// 회원 파트
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("status", 404);
		body.put("message", ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
	}

	// 책 파트
	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleBookNotFoundException(BookNotFoundException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("status", 404);
		body.put("message", ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
	}

	// 리뷰 파트
	@ExceptionHandler(ReviewNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleReviewNotFoundException(ReviewNotFoundException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("status", 404);
		body.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
	}

	@ExceptionHandler(ReviewAlreadyDeletedException.class)
	public ResponseEntity<Map<String, Object>> handleReviewAlreadyDeletedException(ReviewAlreadyDeletedException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("status", 400);
		body.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}

	@ExceptionHandler(ReviewAccessDeniedException.class)
	public ResponseEntity<Map<String, Object>> handleReviewAccessDeniedException(ReviewAccessDeniedException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("status", 403);
		body.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
	}
}
