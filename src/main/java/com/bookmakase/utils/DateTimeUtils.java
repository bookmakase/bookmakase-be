package com.bookmakase.utils;

import java.time.LocalDateTime;

public class DateTimeUtils {
	// 인스턴스화 방지
	private DateTimeUtils() {
	}

	/**
	 * 현재 날짜로부터 이틀 후의 배송 예정일을 계산하여 반환합니다.
	 *
	 * @return 현재 시간 기준 이틀 후의 LocalDateTime 객체
	 */
	public static LocalDateTime getExpectedArrivalDate() {
		return LocalDateTime.now().plusDays(2);
	}
}
