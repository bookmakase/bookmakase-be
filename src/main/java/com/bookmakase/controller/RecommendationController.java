package com.bookmakase.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookmakase.dto.admin.RecommendationPageResponse;
import com.bookmakase.dto.admin.RecommendationResponse;
import com.bookmakase.service.RecommendationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/books")
public class RecommendationController {

	private final RecommendationService recommendationService;

	@PostMapping("/{bookId}/recommend")
	public ResponseEntity<RecommendationResponse> recommendBook(
		@PathVariable Long bookId,
		@AuthenticationPrincipal UserDetails user
	) {
		String email = user.getUsername();
		RecommendationResponse response = recommendationService.recommendBook(bookId, email);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/recommendations")
	public ResponseEntity<RecommendationPageResponse> getAllRecommendations(
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		RecommendationPageResponse responses = recommendationService.getAllRecommendations(page, size);
		return ResponseEntity.ok(responses);
	}

	@DeleteMapping("/recommendations/{recommendationId}")
	public ResponseEntity<Void> deleteRecommendation(
		@PathVariable Long recommendationId,
		@AuthenticationPrincipal UserDetails user
	) {
		String email = user.getUsername();
		recommendationService.deleteRecommendation(recommendationId, email);
		return ResponseEntity.noContent().build();
	}

}
