package com.bookmakase.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemCreateRequest {
	@NotNull(message = "도서 ID는 필수입니다.")
	private Long bookId;

	@NotBlank(message = "도서 제목은 필수입니다.")
	private String title;
	private String contents;

	@NotNull(message = "판매 가격은 필수입니다.")
	@Min(value = 0, message = "판매 가격은 0 이상이어야 합니다.")
	private Integer salePrice;

	@NotNull(message = "주문 수량은 필수입니다.")
	@Min(value = 1, message = "주문 수량은 최소 1개 이상이어야 합니다.")
	private Integer orderQuantity;
}
