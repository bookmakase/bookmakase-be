package com.bookmakase.dto.admin;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookAdminListResponse {
	private List<BookAdminResponseDto> content;
	private PageInfo pageInfo;

	@Getter
	@Setter
	@Builder
	public static class PageInfo {
		private int currentPage;
		private int totalPages;
		private long totalElements;
	}
}
