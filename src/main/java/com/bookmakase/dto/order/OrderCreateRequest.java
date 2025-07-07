package com.bookmakase.dto.order;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequest {
	@NotNull(message = "총 가격은 필수입니다.")
	@Min(value = 0, message = "총 가격은 0 이상이어야 합니다.")
	private Integer totalPrice;

	@NotNull(message = "총 수량은 필수입니다.")
	@Min(value = 1, message = "총 수량은 1 이상이어야 합니다.")
	private Integer totalQuantity;

	@NotBlank(message = "결제 방법은 필수입니다.")
	private String paymentMethod;

	@NotBlank(message = "주문 주소는 필수입니다.")
	private String orderAddress;

	@NotNull(message = "사용된 포인트는 필수입니다.")
	@Min(value = 0, message = "사용된 포인트는 0 이상이어야 합니다.")
	private Integer usedPoint;

	@NotNull(message = "적립될 포인트는 필수입니다.")
	@Min(value = 0, message = "적립될 포인트는 0 이상이어야 합니다.")
	private Integer earningPoint;

	@NotNull(message = "배송비는 필수입니다.")
	@Min(value = 0, message = "배송비는 0 이상이어야 합니다.")
	private Integer deliveryPrice;

	@NotNull(message = "주문 상품 목록은 필수입니다.")
	@NotEmpty(message = "주문 상품 목록은 비어있을 수 없습니다.")
	@Valid
	private List<OrderItemCreateRequest> orderItems;
}
