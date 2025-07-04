package com.bookmakase.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmakase.domain.Comment;
import com.bookmakase.domain.Review;
import com.bookmakase.domain.User;
import com.bookmakase.dto.comment.CommentCreateRequest;
import com.bookmakase.dto.comment.CommentCreateResponse;
import com.bookmakase.dto.comment.CommentUpdateRequest;
import com.bookmakase.dto.comment.CommentUpdateResponse;
import com.bookmakase.exception.comment.CommentAccessDeniedException;
import com.bookmakase.exception.comment.CommentNotFoundException;
import com.bookmakase.exception.review.ReviewAlreadyDeletedException;
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

		// 3. 리뷰가 삭제 상태인지
		if (review.isDeleted()) {
			throw new ReviewAlreadyDeletedException("리뷰가 삭제되어 답글을 작성할 수 없습니다.");
		}

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

	public CommentUpdateResponse updateComment(Long commentId, CommentUpdateRequest request, String email) {
		// 1. 답글이 있는지
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CommentNotFoundException("답글이 존재하지 않습니다."));

		// 2. 사용자가 맞는지
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

		// 3. 답글의 소유자가 맞는지
		if (!comment.getUser().getUserId().equals(user.getUserId())) {
			throw new CommentAccessDeniedException("답글을 수정할 수 있는 권한이 없습니다.");
		}

		comment.setComment(request.getComment());
		comment.setUpdatedAt(LocalDateTime.now());

		return CommentUpdateResponse.builder()
			.commentId(comment.getCommentId())
			.userId(comment.getUser().getUserId())
			.updatedAt(comment.getUpdatedAt())
			.comment(comment.getComment())
			.build();
	}

	public void deleteComment(Long commentId, String email) {
		// 1. 답글이 있는지
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CommentNotFoundException("답글이 존재하지 않습니다."));

		// 2. 사용자가 맞는지
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

		// 3. 답글의 소유자가 맞는지
		if (!comment.getUser().getUserId().equals(user.getUserId())) {
			throw new CommentAccessDeniedException("답글을 삭제할 수 있는 권한이 없습니다.");
		}

		commentRepository.deleteById(commentId);
	}
}
