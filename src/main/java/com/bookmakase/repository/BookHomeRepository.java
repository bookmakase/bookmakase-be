package com.bookmakase.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmakase.domain.Book;

public interface BookHomeRepository extends JpaRepository<Book, Long> {
	List<Book> findAllByOrderByCreatedAtDesc(Pageable pageable);

}
