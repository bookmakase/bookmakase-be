package com.bookmakase.dto.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.bookmakase.domain.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

	private Long orderId; // 주문 아이디

	private LocalDateTime orderDate; // 주문일

	private String orderStatus; // 주문한 아이템 상태

	private LocalDateTime expectedArrivalDate; // 배송중일경우 - 예정일

	private LocalDateTime deliveryDate; // 배송 완료 - 실제 도착일

	private boolean isRefunded; // 환불 여부
	private boolean isReview; // 리뷰 가능 여부

	private Integer totalPrice; // 총 주문 가격
	private Integer totalQuantity; // 총 주문 수량

	private List<OrderItemResponse> orderItems;

	public static OrderResponse from(Order order) {

		// // 주문했을 때 DEFAULT 2일
		// String expectedDate = order.getOrderDate()
		// 	.toLocalDate()
		// 	.plusDays(2)
		// 	.format(DateTimeFormatter.ISO_LOCAL_DATE);

		// 주문에 포함된 책 리스트를 dto로 변환
		List<OrderItemResponse> items = order.getOrderItems().stream()
			.map((item) -> OrderItemResponse.from(item))
			.collect(Collectors.toList());

		return OrderResponse.builder()
			.orderId(order.getOrderId())
			.orderDate(order.getOrderDate())
			.orderStatus(order.getOrderStatus())
			.expectedArrivalDate(order.getExpectedArrivalDate())
			.deliveryDate(order.getDeliveryDate())
			.isRefunded(order.isRefunded())
			.isReview(false)
			.totalPrice(order.getTotalPrice())
			.totalQuantity(order.getTotalQuantity())
			.orderItems(items)
			.build();

	}
}
