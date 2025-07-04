package com.bookmakase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookmakase.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
