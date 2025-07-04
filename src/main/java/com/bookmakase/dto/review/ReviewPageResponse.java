package com.bookmakase.dto.review;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewPageResponse {
	private int page;
	private int size;
	private long totalElements;
	private int totalPages;
	private List<ReviewResponse> reviews;
}
