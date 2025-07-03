package com.bookmakase.dto.review;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewPatchResponse {
	private Long reviewId;
	private Long userId;
	private LocalDateTime updatedAt;
	private boolean isDeleted;
}
