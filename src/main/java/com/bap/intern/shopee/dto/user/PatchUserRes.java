package com.bap.intern.shopee.dto.user;

import com.bap.intern.shopee.dto.BaseRes;
import com.bap.intern.shopee.entity.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PatchUserRes extends BaseRes {
	
	private UserRes user;
	
	public PatchUserRes(User user) {
		status = 200;
		message = "Edit user successfully";
		this.user = new UserRes(user);
	}
}
