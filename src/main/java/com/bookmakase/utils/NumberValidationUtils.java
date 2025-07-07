package com.bookmakase.utils;

public class NumberValidationUtils {
	/**
	 * 주어진 문자열이 유효한 Long 타입 숫자인지 확인합니다.
	 *
	 * @param str 검증할 문자열
	 * @return 문자열이 유효한 Long 타입 숫자이면 true, 그렇지 않으면 false
	 */
	public static boolean isParsableAsLong(String str) {
		if (str == null || str.trim().isEmpty()) {
			return false; // null이거나 빈 문자열은 숫자로 파싱할 수 없음
		}
		try {
			Long.parseLong(str);
			return true;
		} catch (NumberFormatException e) {
			return false; // 숫자로 파싱할 수 없는 경우
		}
	}
}
