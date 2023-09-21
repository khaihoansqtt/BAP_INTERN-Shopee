package com.bap.intern.shopee.dto.auth;

import com.bap.intern.shopee.dto.BaseRes;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRes extends BaseRes {
	
    private final String accessToken;
    private final String refreshToken;
	
	public LoginRes(String accessToken, String refreshToken) {
		status = 200;
		message = "Login successfully";
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
