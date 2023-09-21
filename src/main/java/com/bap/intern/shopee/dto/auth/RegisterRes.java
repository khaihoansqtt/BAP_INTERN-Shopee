package com.bap.intern.shopee.dto.auth;

import com.bap.intern.shopee.dto.BaseRes;
import com.bap.intern.shopee.dto.user.UserRes;
import com.bap.intern.shopee.entity.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegisterRes extends BaseRes {
	
	private UserRes user;
	
	public RegisterRes(User user) {
		status = 201;
		message = "Register account successfully";
		this.user = new UserRes(user);
	}
}
