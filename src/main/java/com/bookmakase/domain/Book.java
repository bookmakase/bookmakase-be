package com.bookmakase.domain;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private Long bookId;

	@Column(nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT")
	private String contents;

	private String isbn;

	private String publisher;

	private String status;

	private String thumbnail;

	private Integer price;

	@Column(name = "sale_price")
	private Integer salePrice;

	@Column(name = "published_at")
	private OffsetDateTime publishedAt;

	@Column(name = "created_at", updatable = false)
	@CreationTimestamp
	private OffsetDateTime createdAt;

	@Column(nullable = false)
	private Integer count = 0;

	@Column(columnDefinition = "TEXT[]")
	private List<String> authors;

	@Column(columnDefinition = "TEXT[]")
	private List<String> translators;

	@OneToMany(mappedBy = "book")
	private List<Review> reviews = new ArrayList<>();

	// orderItem 도메인과 연결 추가
	@OneToMany(mappedBy = "book")
	private List<OrderItem> orderItems = new ArrayList<>();
}
