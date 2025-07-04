package com.bookmakase.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmakase.dto.comment.CommentCreateRequest;
import com.bookmakase.dto.comment.CommentCreateResponse;
import com.bookmakase.exception.common.InvalidException;
import com.bookmakase.service.CommentService;
import com.bookmakase.utils.NumberValidationUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {
	private final CommentService commentService;

	@PostMapping("/reviews/{reviewId}/comments")
	public ResponseEntity<CommentCreateResponse> createComment(
		@PathVariable String reviewId,
		@Valid @RequestBody CommentCreateRequest request,
		@AuthenticationPrincipal UserDetails userDetails
	) {
		// 1. reviewId가 유효한 숫자인지
		if (!NumberValidationUtils.isParsableAsLong(reviewId)) {
			throw new InvalidException("리뷰 id 값이 유효하지 않습니다.");
		}

		String email = userDetails.getUsername();
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(commentService.createComment(Long.parseLong(reviewId), request, email));
	}
}
