package com.bookmakase.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookmakase.dto.book.BookDetailResponse;
import com.bookmakase.dto.book.BookHomeResponse;
import com.bookmakase.dto.book.BookHomeSectionResponse;
import com.bookmakase.dto.book.BookSearchRequest;
import com.bookmakase.service.BookHomeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookHomeController {

	private final BookHomeService bookHomeService;

	@GetMapping("/{bookId}")
	public ResponseEntity<BookDetailResponse> getById(@PathVariable Long bookId) {
		return ResponseEntity.ok(bookHomeService.getBookById(bookId));
	}

	@GetMapping("/home")
	public ResponseEntity<List<BookHomeSectionResponse>> getHomeBooks() {
		return ResponseEntity.ok(bookHomeService.getHomeBooks());
	}

	@GetMapping
	public ResponseEntity<List<BookHomeResponse>> getLatestBooks(
		@RequestParam String type,
		@RequestParam(defaultValue = "10") int limit) {
		if (!"latest".equals(type)) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(bookHomeService.getLatestBooks(limit));
	}

	@GetMapping("/search")
	public ResponseEntity<List<BookHomeResponse>> searchBooks(
		@RequestParam(required = false) String title,
		@RequestParam(required = false) String author
	) {
		BookSearchRequest request = BookSearchRequest.builder()
			.title(title)
			.author(author)
			.build();

		List<BookHomeResponse> result = bookHomeService.searchBooks(request);
		return ResponseEntity.ok(result);
	}

}
