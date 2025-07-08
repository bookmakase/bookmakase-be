package com.bookmakase.exception.recommend;

public class RecommendationNotFoundException extends RuntimeException {
	public RecommendationNotFoundException(Long recommendationId) {
		super("추천 ID " + recommendationId + "에 해당하는 추천 정보를 찾을 수 없습니다.");
	}
}