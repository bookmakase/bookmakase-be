package com.bookmakase.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmakase.dto.book.BookDetailResponse;
import com.bookmakase.dto.book.BookHomeResponse;
import com.bookmakase.dto.book.BookHomeSectionResponse;
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

	@Transactional(readOnly = true)
	public BookHomeSectionResponse getHomeBooks() {
		List<BookHomeResponse> latest = getLatestBooks(10);
		return new BookHomeSectionResponse(latest);
	}

	@Transactional(readOnly = true)
	public List<BookHomeResponse> getLatestBooks(int limit) {
		return bookHomeRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, limit))
			.stream()
			.map(BookHomeResponse::from)
			.collect(Collectors.toList());
	}
}
