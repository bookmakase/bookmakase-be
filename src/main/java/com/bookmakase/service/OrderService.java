package com.bookmakase.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmakase.domain.Book;
import com.bookmakase.domain.Order;
import com.bookmakase.domain.OrderItem;
import com.bookmakase.domain.User;
import com.bookmakase.dto.order.OrderCreateRequest;
import com.bookmakase.dto.order.OrderCreateResponse;
import com.bookmakase.dto.order.OrderItemCreateResponse;
import com.bookmakase.dto.order.OrderPageResponse;
import com.bookmakase.dto.order.OrderResponse;
import com.bookmakase.enums.OrderStatusType;
import com.bookmakase.enums.PaymentMethodType;
import com.bookmakase.exception.book.BookNotFoundException;
import com.bookmakase.exception.user.UserNotFoundException;
import com.bookmakase.repository.BookHomeRepository;
import com.bookmakase.repository.OrderRepository;
import com.bookmakase.repository.UserRepository;
import com.bookmakase.utils.DateTimeUtils;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final BookHomeRepository bookHomeRepository;

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

	public OrderCreateResponse createOrder(OrderCreateRequest orderCreateRequest, String email) {
		// 결제 방식에 대한 enum 값 추출
		PaymentMethodType paymentMethodType = PaymentMethodType.fromString(orderCreateRequest.getPaymentMethod());

		// 1. 사용자가 맞는지
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

		// 2. 주문 생성
		Order order = Order.builder()
			.user(user)
			.totalPrice(orderCreateRequest.getTotalPrice())
			.totalQuantity(orderCreateRequest.getTotalQuantity())
			.orderDate(LocalDateTime.now())
			.orderStatus(OrderStatusType.DELIVERING.getStatusValue())
			.paymentMethod(paymentMethodType.getStatusValue())
			.isRefunded(false)
			.orderAddress(orderCreateRequest.getOrderAddress())
			.usedPoint(orderCreateRequest.getUsedPoint())
			.earningPoint(orderCreateRequest.getEarningPoint())
			.deliveryPrice(orderCreateRequest.getDeliveryPrice())
			.expectedArrivalDate(DateTimeUtils.getExpectedArrivalDate())
			.orderItems(orderCreateRequest.getOrderItems().stream()
				.map(orderItemRequest -> {
					// 책이 있는지
					Book book = bookHomeRepository.findById(orderItemRequest.getBookId())
						.orElseThrow(() -> new BookNotFoundException("도서가 존재하지 않습니다."));

					return OrderItem.builder()
						.book(book)
						.title(orderItemRequest.getTitle())
						.salePrice(orderItemRequest.getSalePrice())
						.orderQuantity(orderItemRequest.getOrderQuantity())
						.contents(orderItemRequest.getContents())
						.build();
				})
				.collect(Collectors.toList()))
			.build();

		// 3. orderItem에 order 정보를 연결
		order.getOrderItems().forEach(orderItem -> orderItem.setOrder(order));

		// 4. order와 orderItem 저장
		Order savedOrder = orderRepository.save(order);

		return OrderCreateResponse.builder()
			.orderId(savedOrder.getOrderId())
			.userId(savedOrder.getUser().getUserId())
			.totalPrice(savedOrder.getTotalPrice())
			.totalQuantity(savedOrder.getTotalQuantity())
			.orderDate(savedOrder.getOrderDate())
			.orderStatus(savedOrder.getOrderStatus())
			.paymentMethod(savedOrder.getPaymentMethod())
			.orderAddress(savedOrder.getOrderAddress())
			.usedPoint(savedOrder.getUsedPoint())
			.earningPoint(savedOrder.getEarningPoint())
			.deliveryPrice(savedOrder.getDeliveryPrice())
			.expectedArrivalDate(savedOrder.getExpectedArrivalDate())
			.orderItems(savedOrder.getOrderItems().stream()
				.map(orderItem -> OrderItemCreateResponse.builder()
					.bookId(orderItem.getBook().getBookId())
					.title(orderItem.getTitle())
					.contents(orderItem.getContents())
					.salePrice(orderItem.getSalePrice())
					.orderQuantity(orderItem.getOrderQuantity())
					.build())
				.collect(Collectors.toList()))
			.build();
	}
}
