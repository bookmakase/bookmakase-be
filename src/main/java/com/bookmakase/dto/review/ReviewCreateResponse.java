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
public class ReviewCreateResponse {
	private Long reviewId;
	private Long bookId;
	private Long userId;
	private byte rating;
	private String content;
	private LocalDateTime createdAt;
}
