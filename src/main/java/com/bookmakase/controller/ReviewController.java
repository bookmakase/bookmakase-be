package com.bookmakase.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmakase.dto.review.ReviewCreateRequest;
import com.bookmakase.dto.review.ReviewCreateResponse;
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

	@PostMapping("/books/{bookId}/reviews")
	public ResponseEntity<ReviewCreateResponse> postReview(
		@PathVariable Long bookId,
		@RequestBody ReviewCreateRequest request,
		@AuthenticationPrincipal UserDetails user
	) {
		String email = user.getUsername();
		return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(bookId, request, email));
	}
}
