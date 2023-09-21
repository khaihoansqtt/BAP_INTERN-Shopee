package com.bap.intern.shopee.exception.customException;

public class CategoryNotExistedException extends RuntimeException {

	public CategoryNotExistedException() {
		super("category is not existed in the system");
		// TODO Auto-generated constructor stub
	}

}
