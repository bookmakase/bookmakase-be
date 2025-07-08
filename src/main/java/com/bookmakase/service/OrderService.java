package com.bookmakase.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmakase.domain.Order;
import com.bookmakase.dto.order.OrderPageResponse;
import com.bookmakase.dto.order.OrderResponse;
import com.bookmakase.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;

	public OrderPageResponse getAllOrders(Long userId, int page, int size) {

		/* ① PageRequest 준비 (0-based로 보정) */
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "orderDate"));

		/* ② 주문 페이지 조회 */
		Page<Order> result = orderRepository.findByUser_UserId(userId, pageable);

		List<OrderResponse> orders = result.getContent().stream()
			.map((order) -> OrderResponse.from(order))
			.toList();

		return OrderPageResponse.from(orders, result);
	}
}
