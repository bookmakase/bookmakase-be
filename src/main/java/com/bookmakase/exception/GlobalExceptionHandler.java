package com.bookmakase.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

	// 회원 파트
	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<Map<String, Object>> handleDuplicateEmailException(DuplicateEmailException ex) {
		Map<String, Object> body = new LinkedHashMap<>();

		body.put("message", ex.getMessage());
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.CONFLICT.value());
		body.put("error", "이메일 중복 오류");

		return new ResponseEntity<>(body, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("status", 404);
		body.put("message", ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
	}

	// 리뷰 파트
	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleBookNotFoundException(BookNotFoundException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("status", 404);
		body.put("message", ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
	}
}
