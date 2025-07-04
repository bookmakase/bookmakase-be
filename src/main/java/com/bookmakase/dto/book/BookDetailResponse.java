package com.bookmakase.dto.book;

import java.util.List;

import com.bookmakase.domain.Book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookDetailResponse {

	private Long bookId;

	private String title;
	private String contents;
	private List<String> authors;
	private String publisher;
	private Integer price;
	private Integer salePrice;
	private String thumbnail;
	private String status;

	public static BookDetailResponse from(Book book) {
		return BookDetailResponse.builder()
			.bookId(book.getBookId())
			.title(book.getTitle())
			.contents(book.getContents())
			.authors(book.getAuthors() != null ? book.getAuthors() : List.of())
			.publisher(book.getPublisher())
			.price(book.getPrice())
			.salePrice(book.getSalePrice())
			.thumbnail(book.getThumbnail())
			.status(book.getStatus())
			.build();
	}
}
