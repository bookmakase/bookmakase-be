package com.bookmakase.dto.review;

import com.bookmakase.enums.ReviewSortType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewListRequest {
	// 페이지네이션
	private int page = 0;
	private int size = 10;

	// 정렬
	private ReviewSortType sortBy = ReviewSortType.LATEST;

	// 내가 쓴 리뷰
	private boolean myReviewOnly = false;
}
