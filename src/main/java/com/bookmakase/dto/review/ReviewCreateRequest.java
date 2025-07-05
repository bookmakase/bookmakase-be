package com.bookmakase.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateRequest {
	@NotNull(message = "별점은 필수 항목입니다.")
	@Min(value = 0, message = "별점은 0점 이상이어야 합니다.")
	@Max(value = 10, message = "별점은 10점 이하여야 합니다.")
	private byte rating;

	@NotBlank(message = "리뷰 내용은 필수 항목입니다.")
	private String content;
}
