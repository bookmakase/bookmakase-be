package com.bookmakase.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateRequest {
	@NotBlank(message = "댓글 내용은 비어 있을 수 없습니다.")
	private String comment;
}
