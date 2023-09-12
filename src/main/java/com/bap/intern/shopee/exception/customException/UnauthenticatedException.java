package com.bap.intern.shopee.exception.customException;

import org.springframework.security.core.AuthenticationException;

public class UnauthenticatedException extends AuthenticationException {

	public UnauthenticatedException(String msg, Throwable cause) {
		super(msg, cause);
		// TODO Auto-generated constructor stub
	}

	public UnauthenticatedException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
