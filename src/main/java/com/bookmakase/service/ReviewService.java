package com.bookmakase.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmakase.domain.Book;
import com.bookmakase.domain.Review;
import com.bookmakase.domain.User;
import com.bookmakase.dto.review.ReviewCreateRequest;
import com.bookmakase.dto.review.ReviewCreateResponse;
import com.bookmakase.dto.review.ReviewPatchResponse;
import com.bookmakase.dto.review.ReviewResponse;
import com.bookmakase.exception.BookNotFoundException;
import com.bookmakase.exception.ReviewNotFoundException;
import com.bookmakase.exception.UserNotFoundException;
import com.bookmakase.repository.BookRepository;
import com.bookmakase.repository.ReviewRepository;
import com.bookmakase.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
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

	public ReviewPatchResponse patchReview(Long reviewId, String email) {
		// 1. 리뷰가 있는지
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new ReviewNotFoundException("존재하지 않는 리뷰입니다."));

		// 2. 사용자가 있는지
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

		log.info("현재 리뷰의 삭제값: {}", review.isDeleted());

		// 삭제된 상태면 복구로, 삭제되지 않은 상태면 삭제로
		review.setDeleted(!review.isDeleted());
		review.setUpdatedAt(LocalDateTime.now());

		return ReviewPatchResponse.builder()
			.reviewId(review.getReviewId())
			.userId(user.getUserId())
			.updatedAt(review.getUpdatedAt())
			.isDeleted(review.isDeleted())
			.build();
	}
}
