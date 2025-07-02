package com.bookmakase.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmakase.dto.review.ReviewResponse;
import com.bookmakase.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewController {
	private final ReviewService reviewService;

	@GetMapping("/books/{bookId}/reviews")
	public ResponseEntity<List<ReviewResponse>> getReviews(@PathVariable Long bookId) {
		return ResponseEntity.ok(reviewService.getReviewsByBookId(bookId));
	}
}
