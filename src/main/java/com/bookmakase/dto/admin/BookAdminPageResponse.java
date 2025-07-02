package com.bookmakase.dto.admin;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bookmakase.domain.Book;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookAdminPageResponse {
	private int page; // 현재 페이지 번호
	private int size; // 페이지당 게시글 수
	private int totalPages; // 전체 페이지 수
	private long totalCount; // 전체 게시글 수
	private List<BookAdminResponse> content;

	public static BookAdminPageResponse from(List<BookAdminResponse> content, Page<Book> pageResult) {

		return BookAdminPageResponse.builder()
			.content(content) // 현재 페이지의 도서들
			.page(pageResult.getNumber() + 1)  // +1은 JPA의 0-based 페이지 인덱스를, 사용자 친화적인 1-based 번호로 맞춰주는 보정 작업
			.totalPages(pageResult.getTotalPages())  // 전체 페이지 수
			.totalCount(pageResult.getTotalElements()) // 전체 도서 수
			.build();
	}
}