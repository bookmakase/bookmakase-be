package com.bookmakase.dto.user;

import com.bookmakase.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointUpdateResponse {
	private Long userId;
	private Long point;

	public static PointUpdateResponse from(User user) {
		return PointUpdateResponse.builder()
			.userId(user.getUserId())
			.point(user.getPoint())
			.build();
	}
}
