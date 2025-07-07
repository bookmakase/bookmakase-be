package com.bookmakase.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethodType {
	GENERAL("일반결제");

	private final String statusValue;

	// *** 중요: 문자열 이름(예: "GENERAL")을 받아서 enum 타입으로 변환하는 메서드 ***
	public static PaymentMethodType fromString(String name) {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Payment method name cannot be null or empty.");
		}
		try {
			return PaymentMethodType.valueOf(name.toUpperCase()); // 대소문자 구별 없이 처리하려면 toUpperCase() 사용
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Unknown payment method name: " + name, e);
		}
	}
}
