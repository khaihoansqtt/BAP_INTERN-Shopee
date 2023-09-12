package com.bap.intern.shopee.dto.auth;

import com.bap.intern.shopee.customValidator.intArrayRange.IntArrayRange;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegisterReq {

	@Email
	@NotBlank
	private String email;

	@NotBlank
	@Size(min = 8, message = "password should have at least 8 characters")
	private String password;

	@NotBlank(message = "name must not be blank")
	private String name;

	@NotEmpty(message = "role must not be blank")
	@IntArrayRange(min = 0, max = 2, message = "role is invalid")
	private int[] roleOrdinals;
}
