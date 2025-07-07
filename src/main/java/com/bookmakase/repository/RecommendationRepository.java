package com.bookmakase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookmakase.domain.Recommendation;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
	boolean existsByUserEmailAndBookBookId(String email, Long bookId);
}
