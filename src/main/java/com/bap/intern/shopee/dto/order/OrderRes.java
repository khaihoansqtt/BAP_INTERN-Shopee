package com.bap.intern.shopee.dto.order;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.bap.intern.shopee.entity.Order;
import com.bap.intern.shopee.entity.OrderItem;
import com.bap.intern.shopee.entity.Order.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRes {
	
	private int orderId;
	private int userId;
	private double totalMoney;
	private OrderStatus orderStatus;
	private Date createdAt;
	private List<OrderItemRes> orderItemResList; 
	
	public OrderRes(Order order) {
		this.orderId = order.getId();
		this.userId = order.getUser().getId();
		this.totalMoney = order.getTotalMoney();
		this.orderStatus = order.getStatus();
		this.createdAt = order.getCreatedAt();
		this.orderItemResList = order.getOrderItems().stream().map(orderItem -> new OrderItemRes(orderItem))
																.collect(Collectors.toList());
	}
}
