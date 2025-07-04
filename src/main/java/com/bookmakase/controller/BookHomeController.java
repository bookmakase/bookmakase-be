package com.bookmakase.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmakase.dto.book.BookDetailResponse;
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

}
