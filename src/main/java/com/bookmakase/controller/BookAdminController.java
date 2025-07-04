package com.bookmakase.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookmakase.dto.admin.BookAdminCreateRequest;
import com.bookmakase.dto.admin.BookAdminDetailResponse;
import com.bookmakase.dto.admin.BookAdminPageResponse;
import com.bookmakase.dto.admin.BookAdminUpdateRequest;
import com.bookmakase.service.BookAdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/books")
@RequiredArgsConstructor
public class BookAdminController {

	private final BookAdminService bookAdminService;

	@GetMapping
	public ResponseEntity<BookAdminPageResponse> getBooks(
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		BookAdminPageResponse response = bookAdminService.getBooks(page, size);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<BookAdminDetailResponse> createBook(@RequestBody BookAdminCreateRequest request) {
		BookAdminDetailResponse response = bookAdminService.createBook(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{bookId}")
	public ResponseEntity<BookAdminDetailResponse> getById(@PathVariable Long bookId) {
		return ResponseEntity.ok(bookAdminService.getBookById(bookId));
	}

	@PutMapping("/{bookId}")
	public ResponseEntity<BookAdminDetailResponse> updateBook(@PathVariable Long bookId,
		@RequestBody BookAdminUpdateRequest request) {
		return ResponseEntity.ok(bookAdminService.updateBook(bookId, request));
	}
}