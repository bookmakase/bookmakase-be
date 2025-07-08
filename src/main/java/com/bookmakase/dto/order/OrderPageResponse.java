package com.bookmakase.dto.order;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bookmakase.domain.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderPageResponse {
	private int page;
	private int size;
	private int totalPages;
	private Long totalCount;
	private List<OrderResponse> orders;

	public static OrderPageResponse from(List<OrderResponse> orders, Page<Order> pageResult) {
		return OrderPageResponse.builder()
			.orders(orders)
			.page(pageResult.getNumber() + 1)
			.size(pageResult.getSize())
			.totalPages(pageResult.getTotalPages())
			.totalCount(pageResult.getTotalElements())
			.build();
	}
}
