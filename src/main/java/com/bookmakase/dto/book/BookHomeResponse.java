package com.bookmakase.dto.book;

import java.util.List;

import com.bookmakase.domain.Book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookHomeResponse {

	private String title;
	private List<String> authors;
	private String thumbnail;

	public static BookHomeResponse from(Book book) {
		return BookHomeResponse.builder()
			.title(book.getTitle())
			.authors(book.getAuthors() != null ? book.getAuthors() : List.of())
			.thumbnail(book.getThumbnail())
			.build();
	}
}
