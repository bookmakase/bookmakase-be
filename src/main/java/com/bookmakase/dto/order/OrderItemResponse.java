package com.bookmakase.dto.order;

import java.time.LocalDateTime;

import com.bookmakase.domain.Book;
import com.bookmakase.domain.Order;
import com.bookmakase.domain.OrderItem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponse {

	private Long bookId;
	private String title; // 책 제목
	private String contents; // 책 줄거리
	private String thumbnail; // 책 이미지
	private int price; // 정가
	private int salePrice; // 할인 금액
	private Integer orderQuantity; // 주문 수량

	private String orderStatus;         // 배송 상태
	private LocalDateTime expectedArrivalDate; // 배송중일 경우 예정일
	private LocalDateTime deliveryDate;        // 배송 완료 실제 도착일

	public static OrderItemResponse from(OrderItem orderItem) {
		Book book = orderItem.getBook();
		Order order = orderItem.getOrder();

		return OrderItemResponse.builder()
			.bookId(book.getBookId())
			.title(book.getTitle())
			.contents(orderItem.getContents())
			.thumbnail(book.getThumbnail())
			.price(book.getPrice())
			.salePrice(book.getSalePrice())
			.orderQuantity(orderItem.getOrderQuantity())
			.orderStatus(order.getOrderStatus())
			.expectedArrivalDate(order.getExpectedArrivalDate())
			.deliveryDate(order.getDeliveryDate())
			.build();
	}
}
