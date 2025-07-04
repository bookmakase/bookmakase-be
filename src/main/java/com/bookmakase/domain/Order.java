package com.bookmakase.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems = new ArrayList<>();
}
