package com.bookmakase.dto.comment;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentPageResponse {
	private int page;
	private int size;
	private long totalElements;
	private int totalPages;
	private List<CommentResponse> comments;
}
