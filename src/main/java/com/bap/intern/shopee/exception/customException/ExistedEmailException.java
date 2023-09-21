package com.bap.intern.shopee.exception.customException;

public class ExistedEmailException extends RuntimeException {

	public ExistedEmailException() {
		super("email is existed in the system");
		// TODO Auto-generated constructor stub
	}
}
