package com.bookmakase.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmakase.domain.Comment;
import com.bookmakase.domain.Review;
import com.bookmakase.domain.User;
import com.bookmakase.dto.comment.CommentCreateRequest;
import com.bookmakase.dto.comment.CommentCreateResponse;
import com.bookmakase.exception.review.ReviewNotFoundException;
import com.bookmakase.exception.user.UserNotFoundException;
import com.bookmakase.repository.CommentRepository;
import com.bookmakase.repository.ReviewRepository;
import com.bookmakase.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;

	public CommentCreateResponse createComment(Long reviewId, CommentCreateRequest request, String email) {
		// 1. 리뷰가 있는지
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new ReviewNotFoundException("존재하지 않는 리뷰입니다."));

		// 2. 사용자가 맞는지
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

		Comment comment = Comment.builder()
			.review(review)
			.user(user)
			.comment(request.getComment())
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();

		Comment savedComment = commentRepository.save(comment);

		return CommentCreateResponse.builder()
			.reviewId(savedComment.getReview().getReviewId())
			.userId(savedComment.getUser().getUserId())
			.commentId(savedComment.getCommentId())
			.createdAt(savedComment.getCreatedAt())
			.comment(savedComment.getComment())
			.build();
	}
}
