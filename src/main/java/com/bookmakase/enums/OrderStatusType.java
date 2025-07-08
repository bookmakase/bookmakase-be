package com.bookmakase.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatusType {
	DELIVERING("배송 중"),
	DELIVERED("배송 완료");

	private final String statusValue;
}
