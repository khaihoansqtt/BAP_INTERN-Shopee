package com.bap.intern.shopee.dto.order;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderItemReq {
	private int productId;
	
	@Min(value = 1)
	private int quantity;
}
