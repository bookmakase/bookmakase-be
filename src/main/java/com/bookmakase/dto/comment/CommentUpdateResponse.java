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
public class CommentUpdateResponse {
	private Long commentId;
	private Long userId;
	private LocalDateTime updatedAt;
	private String comment;
}
