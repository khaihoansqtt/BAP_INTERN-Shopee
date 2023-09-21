package com.bap.intern.shopee.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseRes {
	protected int status;
	protected String message;
	protected long timestamp;
	
	public BaseRes(int status, String message) {
		this.status = status;
		this.message = message;
		this.timestamp = System.currentTimeMillis();
	}
	public BaseRes() {
		this.timestamp = System.currentTimeMillis();
	}
}
