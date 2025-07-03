package com.bookmakase.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmakase.domain.Book;
import com.bookmakase.domain.Review;
import com.bookmakase.domain.User;
import com.bookmakase.dto.review.ReviewCreateRequest;
import com.bookmakase.dto.review.ReviewCreateResponse;
import com.bookmakase.dto.review.ReviewResponse;
import com.bookmakase.exception.BookNotFoundException;
import com.bookmakase.exception.UserNotFoundException;
import com.bookmakase.repository.BookRepository;
import com.bookmakase.repository.ReviewRepository;
import com.bookmakase.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final BookRepository bookRepository;
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public List<ReviewResponse> getReviewsByBookId(Long bookId) {
		List<Review> reviews = reviewRepository.findByBookBookId(bookId);

		return reviews.stream().map(ReviewResponse::from).toList();
	}

	public ReviewCreateResponse createReview(Long bookId, ReviewCreateRequest request, String email) {
		// 1. 책이 있는지
		Book book = bookRepository.findById(bookId)
			.orElseThrow(() -> new BookNotFoundException("존재하지 않는 도서입니다."));

		// 2. 사용자가 맞는지
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

		Review review = Review.builder()
			.book(book)
			.user(user)
			.rating(request.getRating())
			.content(request.getContent())
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.isDeleted(false)
			.build();

		Review savedReview = reviewRepository.save(review);

		return ReviewCreateResponse.builder()
			.reviewId(savedReview.getReviewId())
			.bookId(savedReview.getBook().getBookId())
			.userId(savedReview.getUser().getUserId())
			.createdAt(savedReview.getCreatedAt())
			.rating(savedReview.getRating())
			.content(savedReview.getContent())
			.build();
	}
}
