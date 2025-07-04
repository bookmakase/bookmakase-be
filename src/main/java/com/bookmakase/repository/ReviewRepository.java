package com.bookmakase.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookmakase.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	Page<Review> findByBookBookId(Long bookId, Pageable pageable);
	Page<Review> findByBookBookIdAndUserUserId(Long bookId, Long userId, Pageable pageable);
}
