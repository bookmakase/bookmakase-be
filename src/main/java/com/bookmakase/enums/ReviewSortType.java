package com.bookmakase.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewSortType {
	LATEST("createdAt");

	private final String filterName;

	public static ReviewSortType fromString(String text) {
		for (ReviewSortType b : ReviewSortType.values()) {
			if (b.name().equalsIgnoreCase(text)) {
				return b;
			}
		}

		throw new IllegalArgumentException(text + " 정렬 조건은 존재하지 않습니다.");
	}
}