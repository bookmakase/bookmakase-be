package com.bookmakase.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bookmakase.domain.Book;
import com.bookmakase.dto.admin.BookAdminDto;
import com.bookmakase.dto.admin.BookAdminListResponse;
import com.bookmakase.repository.BookAdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookAdminService {

	private final BookAdminRepository bookAdminRepository;

	public BookAdminListResponse getBooks(int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, "bookId"));
		Page<Book> result = bookAdminRepository.findAll(pageable);

		List<BookAdminDto> content = result.stream().map(book -> BookAdminDto.builder()
			.bookId(book.getBookId())
			.title(book.getTitle())
			.authors(book.getAuthors())
			.isbn(book.getIsbn())
			.createdAt(book.getCreatedAt())
			.status(book.getStatus())
			.count(book.getCount())
			.build()
		).toList();

		return BookAdminListResponse.builder()
			.content(content)
			.pageInfo(BookAdminListResponse.PageInfo.builder()
				.currentPage(page)
				.totalPages(result.getTotalPages())
				.totalElements(result.getTotalElements())
				.build())
			.build();
	}
}
