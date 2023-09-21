package com.bap.intern.shopee.dto.order;

import com.bap.intern.shopee.dto.BaseRes;
import com.bap.intern.shopee.entity.Order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostOrderRes extends BaseRes {
	private OrderRes order;
	
	public PostOrderRes(Order order) {
		super();
		message = "order successfully";
		this.order = new OrderRes(order);
	}
}
