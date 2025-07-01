package com.bookmakase.dto.admin;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookAdminResponseDto {
	private Long bookId;
	private String title;
	private List<String> authors;
	private String isbn;
	private OffsetDateTime createdAt;
	private String status;
	private Integer count;
}
