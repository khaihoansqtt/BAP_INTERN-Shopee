package com.bap.intern.shopee.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginReq {
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String password;
}
