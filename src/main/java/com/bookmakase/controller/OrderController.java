package com.bookmakase.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookmakase.domain.User;
import com.bookmakase.dto.order.OrderCreateRequest;
import com.bookmakase.dto.order.OrderCreateResponse;
import com.bookmakase.dto.order.OrderPageResponse;
import com.bookmakase.service.AuthService;
import com.bookmakase.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {
	private final OrderService orderService;
	private final AuthService authService;

	/**
	 * 내 구매목록 페이지 조회
	 *
	 * @param page  1-based 페이지 번호 (기본 1)
	 * @param size  한 페이지당 주문 건수 (기본 4)
	 */

	@GetMapping("/order-list")
	public ResponseEntity<OrderPageResponse> getAllOrders(
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "4") int size
	) {

		User current = authService.getCurrentUser();

		if (current == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		OrderPageResponse response = orderService.getAllOrders(current.getUserId(), page, size);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/order-list")
	public ResponseEntity<OrderCreateResponse> createOrder(
		@AuthenticationPrincipal UserDetails user,
		@Valid @RequestBody OrderCreateRequest request
	) {
		String email = user.getUsername();
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request, email));
	}
}
