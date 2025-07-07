package com.bookmakase.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookmakase.domain.Book;

public interface BookHomeRepository extends JpaRepository<Book, Long> {
	List<Book> findAllByOrderByCreatedAtDesc(Pageable pageable);

	@Query(value = "SELECT * FROM books WHERE " +
		"LOWER(title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		"OR EXISTS (SELECT 1 FROM unnest(authors) a WHERE LOWER(a) LIKE LOWER(CONCAT('%', :keyword, '%')))",
		nativeQuery = true)
	List<Book> searchByTitleOrAuthor(@Param("keyword") String keyword);
}
