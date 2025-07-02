package com.bookmakase.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PointUpdateRequest {

	@NotNull(message = "point 값은 필수입니다.")
	private Long point;




}
