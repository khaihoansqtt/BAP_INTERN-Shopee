package com.bap.intern.shopee.dto.order;

import com.bap.intern.shopee.entity.OrderItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRes {

	private int id;
	private int productId;
	private int quantity;
	private double price;
	
	public OrderItemRes(OrderItem orderItem) {
		id = orderItem.getId();
		productId = orderItem.getProduct().getId();
		quantity = orderItem.getQuantity();
		price = orderItem.getPrice();
	}
}
