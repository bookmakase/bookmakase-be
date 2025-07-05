package com.bookmakase.dto.review;

import java.time.LocalDateTime;

import com.bookmakase.domain.Review;
import com.bookmakase.dto.user.OneUserResponse;

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
	private OneUserResponse user;
	private byte rating;
	private String content;
	private LocalDateTime updatedAt;
	private boolean isDeleted;
}
