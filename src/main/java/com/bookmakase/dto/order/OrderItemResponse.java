package com.bookmakase.dto.order;

import java.time.LocalDateTime;
import java.util.List;

import com.bookmakase.domain.Book;
import com.bookmakase.domain.OrderItem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponse {

	private Long bookId;
	private String title;
	private List<String> author;
	private String isbn;
	private LocalDateTime orderDate;
	private String status;
	private Integer count;

	public static OrderItemResponse from(OrderItem orderItem) {
		Book book = orderItem.getBook();

		return OrderItemResponse.builder()
			.bookId(book.getBookId())
			.title(book.getTitle())
			.author(book.getAuthors())
			.isbn(book.getIsbn())
			.orderDate(LocalDateTime.now())
			.status(book.getStatus())
			.count(book.getCount())
			.build();

	}

}
