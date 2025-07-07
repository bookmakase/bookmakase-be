package com.bookmakase.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmakase.domain.Book;
import com.bookmakase.domain.Review;
import com.bookmakase.domain.User;
import com.bookmakase.dto.review.ReviewCreateRequest;
import com.bookmakase.dto.review.ReviewCreateResponse;
import com.bookmakase.dto.review.ReviewListRequest;
import com.bookmakase.dto.review.ReviewPageResponse;
import com.bookmakase.dto.review.ReviewPatchResponse;
import com.bookmakase.dto.review.ReviewResponse;
import com.bookmakase.dto.review.ReviewUpdateRequest;
import com.bookmakase.dto.review.ReviewUpdateResponse;
import com.bookmakase.dto.user.OneUserResponse;
import com.bookmakase.exception.auth.UnauthorizedException;
import com.bookmakase.exception.book.BookNotFoundException;
import com.bookmakase.exception.review.ReviewAccessDeniedException;
import com.bookmakase.exception.review.ReviewAlreadyDeletedException;
import com.bookmakase.exception.review.ReviewNotFoundException;
import com.bookmakase.exception.user.UserNotFoundException;
import com.bookmakase.repository.BookHomeRepository;
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
	private final BookHomeRepository bookRepository;
	private final UserRepository userRepository;
	private final AuthService authService;

	@Transactional(readOnly = true)
	public ReviewPageResponse getAllReviewsByBookId(Long bookId, ReviewListRequest request) {
		// 1. 책이 있는지
		Book book = bookRepository.findById(bookId)
			.orElseThrow(() -> new BookNotFoundException("존재하지 않는 도서입니다."));

		// 2. 내가 쓴 리뷰 필터에 대한 로그인 검증
		Long userId = null;
		if (request.isMyReviewOnly()) {
			User user = authService.getCurrentUser();
			if (user == null) {
				throw new UnauthorizedException("로그인이 필요합니다.");
			}
			userId = user.getUserId();
		}

		Sort sort = Sort.by(Sort.Direction.DESC, request.getSortBy().getFilterName());
		Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

		Page<Review> reviews;
		if (request.isMyReviewOnly()) {
			reviews = reviewRepository.findByBookBookIdAndUserUserId(book.getBookId(), userId, pageable);
		} else {
			reviews = reviewRepository.findByBookBookId(book.getBookId(), pageable);
		}

		Page<ReviewResponse> reviewsPage = reviews.map(
			review -> {
				OneUserResponse oneUserResponse = new OneUserResponse();
				oneUserResponse.setUserId(review.getUser().getUserId());
				oneUserResponse.setUsername(review.getUser().getUsername());

				return ReviewResponse.builder()
					.reviewId(review.getReviewId())
					.user(oneUserResponse)
					.rating(review.getRating())
					.content(review.getContent())
					.updatedAt(review.getUpdatedAt())
					.isDeleted(review.isDeleted())
					.build();
			}
		);

		return ReviewPageResponse.builder()
			.page(reviewsPage.getNumber())
			.size(reviewsPage.getSize())
			.totalElements(reviewsPage.getTotalElements())
			.totalPages(reviewsPage.getTotalPages())
			.reviews(reviewsPage.getContent())
			.build();
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

	public ReviewUpdateResponse updateReview(Long reviewId, ReviewUpdateRequest request, String email) {
		// 1. 리뷰가 있는지
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new ReviewNotFoundException("존재하지 않는 리뷰입니다."));

		// 2. 삭제된 리뷰인지
		if (review.isDeleted()) {
			throw new ReviewAlreadyDeletedException("이미 삭제된 리뷰입니다.");
		}

		// 3. 사용자가 있는지
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

		// 4. 리뷰의 소유자가 맞는지
		if (!review.getUser().getUserId().equals(user.getUserId())) {
			throw new ReviewAccessDeniedException("리뷰를 수정할 수 있는 권한이 없습니다.");
		}

		review.setRating(request.getRating());
		review.setContent(request.getContent());
		review.setUpdatedAt(LocalDateTime.now());

		return ReviewUpdateResponse.builder()
			.reviewId(review.getReviewId())
			.userId(user.getUserId())
			.updatedAt(review.getUpdatedAt())
			.rating(review.getRating())
			.content(review.getContent())
			.build();
	}

	public ReviewPatchResponse patchReview(Long reviewId, String email) {
		// 1. 리뷰가 있는지
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new ReviewNotFoundException("존재하지 않는 리뷰입니다."));

		// 2. 사용자가 있는지
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

		// 3. 리뷰의 소유자가 맞는지
		if (!review.getUser().getUserId().equals(user.getUserId())) {
			if (review.isDeleted()) {
				throw new ReviewAccessDeniedException("리뷰를 복구할 수 있는 권한이 없습니다.");
			} else {
				throw new ReviewAccessDeniedException("리뷰를 삭제할 수 있는 권한이 없습니다.");
			}
		}

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
