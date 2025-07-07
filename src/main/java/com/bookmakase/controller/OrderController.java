package com.bookmakase.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmakase.dto.order.OrderCreateRequest;
import com.bookmakase.dto.order.OrderCreateResponse;
import com.bookmakase.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {
	private final OrderService orderService;

	@PostMapping("/order-list")
	public ResponseEntity<OrderCreateResponse> createOrder(
		@AuthenticationPrincipal UserDetails user,
		@Valid @RequestBody OrderCreateRequest request
	) {
		String email = user.getUsername();
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request, email));
	}
}
