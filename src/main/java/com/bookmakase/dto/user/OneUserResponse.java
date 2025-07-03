package com.bookmakase.dto.user;

import com.bookmakase.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OneUserResponse {
	private Long userId;
	private String username;

	public static OneUserResponse from(User user) {
		return OneUserResponse.builder()
			.userId(user.getUserId())
			.username(user.getUsername())
			.build();
	}
}
