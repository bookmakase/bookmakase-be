package com.bookmakase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmakase.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
