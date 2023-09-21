package com.bap.intern.shopee.exception.customException;

public class ProductNotExistedException extends RuntimeException {

	public ProductNotExistedException() {
		super("product is not existed in the system");
		// TODO Auto-generated constructor stub
	}

}
