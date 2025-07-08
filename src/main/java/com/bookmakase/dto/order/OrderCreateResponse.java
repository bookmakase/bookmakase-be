package com.bookmakase.dto.order;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreateResponse {
	private Long orderId;
	private Long userId;
	private Integer totalPrice;
	private Integer totalQuantity;
	private LocalDateTime orderDate;
	private String orderStatus;
	private String paymentMethod;
	private String orderAddress;
	private Integer usedPoint;
	private Integer earningPoint;
	private Integer deliveryPrice;
	private LocalDateTime expectedArrivalDate;
	private List<OrderItemCreateResponse> orderItems;
}
