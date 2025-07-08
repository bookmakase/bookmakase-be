package com.bookmakase.dto.admin;

import java.time.OffsetDateTime;

import com.bookmakase.domain.Recommendation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationResponse {

	private Long recommendedBookId;
	private Long userId;
	private String username;
	private Long bookId;
	private String title;
	private OffsetDateTime recommendedAt;

	public static RecommendationResponse from(Recommendation recommendation) {
		return RecommendationResponse.builder()
			.recommendedBookId(recommendation.getRecommendedBookId())
			.userId(recommendation.getUser().getUserId())
			.username(recommendation.getUser().getUsername())
			.bookId(recommendation.getBook().getBookId())
			.title(recommendation.getBook().getTitle())
			.recommendedAt(recommendation.getRecommendedAt())
			.build();
	}
}
