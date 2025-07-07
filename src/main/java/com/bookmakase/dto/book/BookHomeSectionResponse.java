package com.bookmakase.dto.book;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookHomeSectionResponse {
	private List<BookHomeResponse> latestBooks;

}
