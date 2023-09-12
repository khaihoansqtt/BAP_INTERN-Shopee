package com.bap.intern.shopee.dto.order;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostOrderReq {
	private List<OrderItemReq> orderItemReqList;
}
