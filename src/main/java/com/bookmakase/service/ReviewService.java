package com.bookmakase.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmakase.domain.Review;
import com.bookmakase.dto.review.ReviewResponse;
import com.bookmakase.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
	private final ReviewRepository reviewRepository;

	@Transactional(readOnly = true)
	public List<ReviewResponse> getReviewsByBookId(Long bookId) {
		List<Review> reviews = reviewRepository.findByBookBookId(bookId);

		return reviews.stream().map(ReviewResponse::from).toList();
	}
}
