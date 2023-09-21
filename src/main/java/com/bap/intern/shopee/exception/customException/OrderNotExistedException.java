package com.bap.intern.shopee.exception.customException;

public class OrderNotExistedException extends RuntimeException {

	public OrderNotExistedException() {
		super("order is not existed in the system");
		// TODO Auto-generated constructor stub
	}
}
