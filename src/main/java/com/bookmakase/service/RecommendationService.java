package com.bookmakase.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmakase.domain.Book;
import com.bookmakase.domain.Recommendation;
import com.bookmakase.domain.User;
import com.bookmakase.dto.admin.RecommendationPageResponse;
import com.bookmakase.dto.admin.RecommendationResponse;
import com.bookmakase.exception.book.BookNotFoundException;
import com.bookmakase.exception.recommend.DuplicateRecommendationException;
import com.bookmakase.exception.recommend.RecommendationNotFoundException;
import com.bookmakase.exception.user.UserNotFoundException;
import com.bookmakase.repository.BookAdminRepository;
import com.bookmakase.repository.RecommendationRepository;
import com.bookmakase.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationService {

	private final RecommendationRepository recommendationRepository;
	private final BookAdminRepository bookAdminRepository;
	private final UserRepository userRepository;

	@Transactional
	public RecommendationResponse recommendBook(Long bookId, String email) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

		Book book = bookAdminRepository.findById(bookId)
			.orElseThrow(() -> new BookNotFoundException("존재하지 않는 도서입니다."));

		// ✅ 중복 추천 방지
		if (recommendationRepository.existsByUserEmailAndBookBookId(user.getEmail(), bookId)) {
			throw new DuplicateRecommendationException("중복 추천입니다.");
		}

		Recommendation recommendation = recommendationRepository.save(
			Recommendation.builder()
				.book(book)
				.user(user)
				.recommendedAt(OffsetDateTime.now(ZoneOffset.ofHours(9))) // ✅ 예외 해결된 부분
				.build()
		);

		return RecommendationResponse.from(recommendation);
	}

	@Transactional(readOnly = true)
	public RecommendationPageResponse getAllRecommendations(int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "recommendedAt"));

		Page<Recommendation> result = recommendationRepository.findAll(pageable);

		List<RecommendationResponse> content = result.getContent().stream()
			.map(RecommendationResponse::from)
			.toList();

		return RecommendationPageResponse.from(content, result);
	}

	@Transactional
	public void deleteRecommendation(Long recommendationId, String email) {
		Recommendation recommendation = recommendationRepository.findById(recommendationId)
			.orElseThrow(() -> new RecommendationNotFoundException(recommendationId));

		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

		// ✅ 본인 추천이 아닌 경우 예외
		if (!recommendation.getUser().getUserId().equals(user.getUserId())) {
			throw new AccessDeniedException("자신이 추천한 도서만 삭제할 수 있습니다.");
		}

		recommendationRepository.delete(recommendation);
	}

	@Transactional(readOnly = true)
	public boolean isBookRecommended(Long bookId) {
		return recommendationRepository.existsByBookBookId(bookId);
	}

}
