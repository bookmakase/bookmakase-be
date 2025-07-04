package com.bookmakase.dto.comment;

import java.time.LocalDateTime;

import com.bookmakase.dto.user.OneUserResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
	private Long commentId;
	private Long reviewId;
	private OneUserResponse user;
	private LocalDateTime updatedAt;
	private String comment;
}
