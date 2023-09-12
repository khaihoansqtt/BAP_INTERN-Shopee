package com.bap.intern.shopee.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommonRes {
	protected int status;
	protected String message;
	protected long timestamp;
	
	public CommonRes(int status, String message) {
		this.status = status;
		this.message = message;
		this.timestamp = System.currentTimeMillis();
	}
	public CommonRes() {
		this.timestamp = System.currentTimeMillis();
	}
}
