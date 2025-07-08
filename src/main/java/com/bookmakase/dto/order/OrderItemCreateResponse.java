package com.bookmakase.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemCreateResponse {
	private Long bookId;
	private String title;
	private String contents;
	private Integer salePrice;
	private Integer orderQuantity;
}
