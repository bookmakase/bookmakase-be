package com.bookmakase.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmakase.dto.review.ReviewCreateRequest;
import com.bookmakase.dto.review.ReviewCreateResponse;
import com.bookmakase.dto.review.ReviewListRequest;
import com.bookmakase.dto.review.ReviewPageResponse;
import com.bookmakase.dto.review.ReviewPatchResponse;
import com.bookmakase.dto.review.ReviewUpdateRequest;
import com.bookmakase.dto.review.ReviewUpdateResponse;
import com.bookmakase.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewController {
	private final ReviewService reviewService;

	@GetMapping("/books/{bookId}/reviews")
	public ResponseEntity<ReviewPageResponse> getAllReviews(
		@PathVariable Long bookId,
		@ModelAttribute ReviewListRequest request
	) {
		return ResponseEntity.ok(reviewService.getAllReviewsByBookId(bookId, request));
	}

	@PostMapping("/books/{bookId}/reviews")
	public ResponseEntity<ReviewCreateResponse> postReview(
		@PathVariable Long bookId,
		@Valid @RequestBody ReviewCreateRequest request,
		@AuthenticationPrincipal UserDetails user
	) {
		String email = user.getUsername();
		return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(bookId, request, email));
	}

	@PutMapping("/reviews/{reviewId}")
	public ResponseEntity<ReviewUpdateResponse> updateReview(
		@PathVariable Long reviewId,
		@RequestBody ReviewUpdateRequest request,
		@AuthenticationPrincipal UserDetails user
	) {
		String email = user.getUsername();
		return ResponseEntity.ok(reviewService.updateReview(reviewId, request, email));
	}

	@PatchMapping("/reviews/{reviewId}")
	public ResponseEntity<ReviewPatchResponse> patchReview(
		@PathVariable Long reviewId,
		@AuthenticationPrincipal UserDetails user
	) {
		String email = user.getUsername();
		return ResponseEntity.ok(reviewService.patchReview(reviewId, email));
	}
}
