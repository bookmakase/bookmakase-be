package com.bookmakase.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookmakase.domain.Recommendation;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
	boolean existsByUserEmailAndBookBookId(String email, Long bookId);

	boolean existsByBookBookId(Long bookId);

	List<Recommendation> findAllByOrderByRecommendedAtDesc(Pageable pageable);

}
