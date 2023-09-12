package com.bap.intern.shopee.dto.order;

import com.bap.intern.shopee.dto.CommonRes;
import com.bap.intern.shopee.entity.Order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostOrderRes extends CommonRes {
	private OrderRes order;
	public PostOrderRes(Order order) {
		super();		
		this.order = new OrderRes(order);
	}
}
