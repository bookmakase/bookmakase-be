package com.bookmakase.dto.book;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookHomeSectionResponse {

	private String type;
	private String title;
	private List<BookHomeResponse> books;

	public static BookHomeSectionResponse of(String type, String title, List<BookHomeResponse> books) {
		return new BookHomeSectionResponse(type, title, books);
	}
}