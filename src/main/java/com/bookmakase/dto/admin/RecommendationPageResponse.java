package com.bookmakase.dto.admin;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bookmakase.domain.Recommendation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecommendationPageResponse {

	private int page;             // 현재 페이지 번호 (1-based)
	private int size;            // 페이지당 추천 수
	private int totalPages;      // 전체 페이지 수
	private long totalCount;     // 전체 추천 수
	private List<RecommendationResponse> content;

	public static RecommendationPageResponse from(List<RecommendationResponse> content,
		Page<Recommendation> pageResult) {
		return RecommendationPageResponse.builder()
			.content(content)
			.page(pageResult.getNumber() + 1)         // 0-based → 1-based 변환
			.size(pageResult.getSize())
			.totalPages(pageResult.getTotalPages())
			.totalCount(pageResult.getTotalElements())
			.build();
	}
}
