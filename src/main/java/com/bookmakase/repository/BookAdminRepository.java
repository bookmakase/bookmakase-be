package com.bookmakase.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookmakase.domain.Book;

@Repository
public interface BookAdminRepository extends JpaRepository<Book, Long> {
	Page<Book> findAll(Pageable pageable);

	// 메서드 이름 기반 쿼리
	// Spring Data JPA가 findAll(Pageable pageable) 메서드를 인식하고 자동 구현
}
