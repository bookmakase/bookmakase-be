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
public class BookAdminDetailResponse {

	private Long bookId;
	private String title;
	private String contents;
	private String isbn;
	private OffsetDateTime publishedAt;

	private List<String> authors;
	private List<String> translators;
	private String publisher;

	private Integer price;
	private Integer salePrice;
	private Integer count;

	private String thumbnail;
	private String status;
	private OffsetDateTime createdAt;

	public static BookAdminDetailResponse from(Book book) {
		return BookAdminDetailResponse.builder()
			.bookId(book.getBookId())
			.title(book.getTitle())
			.contents(book.getContents())
			.isbn(book.getIsbn())
			.publishedAt(
				book.getPublishedAt() != null ? book.getPublishedAt() : null)
			.authors(book.getAuthors() != null ? book.getAuthors() : List.of())
			.translators(book.getTranslators() != null ? book.getTranslators() : List.of())
			.publisher(book.getPublisher())
			.price(book.getPrice())
			.salePrice(book.getSalePrice())
			.count(book.getCount())
			.thumbnail(book.getThumbnail())
			.status(book.getStatus())
			.createdAt(book.getCreatedAt())
			.build();
	}
}
