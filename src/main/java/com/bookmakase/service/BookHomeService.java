package com.bookmakase.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmakase.domain.Book;
import com.bookmakase.dto.book.BookDetailResponse;
import com.bookmakase.dto.book.BookHomeResponse;
import com.bookmakase.dto.book.BookHomeSectionResponse;
import com.bookmakase.dto.book.BookSearchRequest;
import com.bookmakase.exception.book.BookNotFoundException;
import com.bookmakase.repository.BookHomeRepository;
import com.bookmakase.repository.RecommendationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookHomeService {
	private final BookHomeRepository bookHomeRepository;
	private final RecommendationRepository recommendationRepository;

	@Transactional(readOnly = true)
	public BookDetailResponse getBookById(Long bookId) {
		return bookHomeRepository.findById(bookId)
			.map(BookDetailResponse::from)
			.orElseThrow(() -> new BookNotFoundException("존재하지 않는 도서입니다."));
	}

	@Transactional(readOnly = true)
	public List<BookHomeSectionResponse> getHomeBooks() {
		List<BookHomeResponse> recommended = getRecommendedBooks(18);
		List<BookHomeResponse> latest = getLatestBooks(18);

		return List.of(
			BookHomeSectionResponse.of("recommended", "추천 도서", recommended),
			BookHomeSectionResponse.of("latest", "최신 도서", latest)
		);
	}

	@Transactional(readOnly = true)
	public List<BookHomeResponse> getLatestBooks(int limit) {
		return bookHomeRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, limit))
			.stream()
			.map(BookHomeResponse::from)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<BookHomeResponse> getRecommendedBooks(int limit) {
		return recommendationRepository.findAllByOrderByRecommendedAtDesc(PageRequest.of(0, limit))
			.stream()
			.map(recommendation -> BookHomeResponse.from(recommendation.getBook()))
			.collect(Collectors.toList());
	}

	public List<BookHomeResponse> searchBooks(BookSearchRequest request) {
		String keyword = Optional.ofNullable(request.getTitle())
			.filter(t -> !t.isBlank())
			.orElse(request.getAuthor());

		if (keyword == null || keyword.isBlank()) {
			return Collections.emptyList();
		}

		List<Book> books = bookHomeRepository.searchByTitleOrAuthor(keyword);
		return books.stream()
			.map(BookHomeResponse::from)
			.collect(Collectors.toList());
	}

}
