package com.bap.intern.shopee.dto.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderItemReq {
	private int productId;
	private int quantity;
}
