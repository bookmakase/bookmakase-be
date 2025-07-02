package com.bookmakase.dto.admin;

import java.time.OffsetDateTime;
import java.util.List;

import com.bookmakase.domain.Book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookAdminResponse {
	private Long bookId;
	private String title;
	private List<String> authors;
	private String isbn;
	private OffsetDateTime createdAt;
	private String status;
	private Integer count;

	public static BookAdminResponse from(Book book) {
		return BookAdminResponse.builder()
			.bookId(book.getBookId())
			.title(book.getTitle())
			.authors(book.getAuthors())
			.isbn(book.getIsbn())
			.status(book.getStatus())
			.count(book.getCount())
			.createdAt(book.getCreatedAt())
			.build();
	}
}