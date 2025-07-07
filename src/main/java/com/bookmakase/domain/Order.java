package com.bookmakase.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "total_price")
	private Integer totalPrice;

	// 수량 없어서 추가한 필드
	@Column(name = "total_quantity")
	private Integer totalQuantity;

	@Column(name = "order_date")
	private LocalDateTime orderDate;

	@Column(name = "order_status")
	private String orderStatus;

	@Column(name = "payment_method")
	private String paymentMethod;

	@Column(name = "is_refunded")
	private boolean isRefunded;

	@Column(name = "order_address")
	private String orderAddress;

	@Column(name = "used_point")
	private Integer usedPoint;

	@Column(name = "earning_point")
	private Integer earningPoint;

	@Column(name = "delivery_price")
	private Integer deliveryPrice;

	// 아래의 2개 필드 추가
	@Column(name = "expected_arrival_date")
	private LocalDateTime expectedArrivalDate; // 배송중일경우 - 예정일
	@Column(name = "delivery_date")
	private LocalDateTime deliveryDate; // 배송 완료 - 실제 도착일

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();
}
