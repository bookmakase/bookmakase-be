package com.bookmakase.dto.comment;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentCreateResponse {
	private Long reviewId;
	private Long userId;
	private Long commentId;
	private LocalDateTime createdAt;
	private String comment;
}
