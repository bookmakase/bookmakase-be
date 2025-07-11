package com.bookmakase.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmakase.domain.Book;
import com.bookmakase.dto.admin.BookAdminCreateRequest;
import com.bookmakase.dto.admin.BookAdminDetailResponse;
import com.bookmakase.dto.admin.BookAdminPageResponse;
import com.bookmakase.dto.admin.BookAdminResponse;
import com.bookmakase.dto.admin.BookAdminUpdateRequest;
import com.bookmakase.exception.book.BookNotFoundException;
import com.bookmakase.repository.BookAdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookAdminService {

	private final BookAdminRepository bookAdminRepository;
	private final RecommendationService recommendationService;

	public BookAdminPageResponse getBooks(int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, "bookId"));
		Page<Book> result = bookAdminRepository.findAll(pageable);

		List<BookAdminResponse> content = result.getContent().stream()
			.map(book -> {
				boolean isRecommended = recommendationService.isBookRecommended(book.getBookId());
				return BookAdminResponse.from(book, isRecommended);
			})
			.toList();

		return BookAdminPageResponse.from(content, result);
	}

	public BookAdminDetailResponse createBook(BookAdminCreateRequest request) {
		Book saved = bookAdminRepository.save(request.toDomain());
		return BookAdminDetailResponse.from(saved);
	}

	@Transactional(readOnly = true)
	public BookAdminDetailResponse getBookById(Long bookId) {
		return bookAdminRepository.findById(bookId)
			.map(BookAdminDetailResponse::from)
			.orElseThrow(() -> new BookNotFoundException("존재하지 않는 도서입니다."));
	}

	public BookAdminDetailResponse updateBook(Long bookId, BookAdminUpdateRequest request) {
		Book book = bookAdminRepository.findById(bookId)
			.orElseThrow(() -> new BookNotFoundException("존재하지 않는 도서입니다."));

		book.setTitle(request.getTitle());
		book.setContents(request.getContents());
		book.setIsbn(request.getIsbn());
		book.setAuthors(request.getAuthors());
		book.setTranslators(request.getTranslators());
		book.setPublisher(request.getPublisher());
		book.setPrice(request.getPrice());
		book.setSalePrice(request.getSalePrice());
		book.setCount(request.getCount());
		book.setThumbnail(request.getThumbnail());
		book.setStatus(request.getStatus());

		if (request.getPublishedAt() != null) {
			book.setPublishedAt(request.getPublishedAt());
		} else {
			book.setPublishedAt(null);
		}

		return BookAdminDetailResponse.from(book);
	}
}
