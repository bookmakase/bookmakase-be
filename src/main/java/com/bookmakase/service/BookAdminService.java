package com.bookmakase.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bookmakase.domain.Book;
import com.bookmakase.dto.admin.BookAdminPageResponse;
import com.bookmakase.dto.admin.BookAdminResponse;
import com.bookmakase.repository.BookAdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookAdminService {

	private final BookAdminRepository bookAdminRepository;

	public BookAdminPageResponse getBooks(int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, "bookId"));
		// Spring의 페이지 인덱스는 0부터 시작하므로, 프론트에서 1을 넘기면 0으로 맞춰줌

		Page<Book> result = bookAdminRepository.findAll(pageable);

		List<BookAdminResponse> content = result.getContent().stream()
			.map(BookAdminResponse::from)
			.toList();
		// Book 도메인을 응답용 DTO BookAdminResponse로 변환

		return BookAdminPageResponse.from(content, result);
	}
}
