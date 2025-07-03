package com.bookmakase.dto.review;

import java.time.LocalDateTime;

import com.bookmakase.domain.Review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponse {
	private Long reviewId;
	private byte rating;
	private String content;
	private LocalDateTime updatedAt;
	private boolean isDeleted;

	public static ReviewResponse from(Review review) {
		return ReviewResponse.builder()
			.reviewId(review.getReviewId())
			.rating(review.getRating())
			.content(review.getContent())
			.updatedAt(review.getUpdatedAt())
			.isDeleted(review.isDeleted())
			.build();
	}
}
