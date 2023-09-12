package com.bap.intern.shopee.dto.user;

import com.bap.intern.shopee.entity.User;
import com.bap.intern.shopee.entity.User.Role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRes {
	private int id;
	private String email;
	private String password;
	private String name;
	private String[] roles;
	
	public UserRes(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.name = user.getName();
		this.roles = user.getRoles().stream()  
			    .map(Role::getName)
			    .toArray(String[]::new);
	}
	
	
}
