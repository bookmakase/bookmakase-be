package com.bookmakase.dto.user;

import java.time.LocalDateTime;

import com.bookmakase.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressUpdateResponse {

	private Long userId;
	private String address;
	private LocalDateTime updateTime;

	public static AddressUpdateResponse from(User user) {
		return AddressUpdateResponse.builder()
			.userId(user.getUserId())
			.address(user.getAddress())
			.updateTime(user.getUpdatedAt())
			.build();
	}
}
