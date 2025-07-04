package com.bookmakase.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmakase.dto.book.BookDetailResponse;
import com.bookmakase.exception.book.BookNotFoundException;
import com.bookmakase.repository.BookHomeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookHomeService {
	private final BookHomeRepository bookHomeRepository;

	@Transactional(readOnly = true)
	public BookDetailResponse getBookById(Long bookId) {
		return bookHomeRepository.findById(bookId)
			.map(BookDetailResponse::from)
			.orElseThrow(() -> new BookNotFoundException("존재하지 않는 도서입니다."));
	}
}
