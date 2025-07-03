package com.bookmakase.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IntroUpdateRequest {

	@NotNull(message = "내 소개필드 값은 필수입니다.")
	private String intro;
}
