package com.bap.intern.shopee.dto.user;

import com.bap.intern.shopee.customValidator.intArrayRange.IntArrayRange;
import com.bap.intern.shopee.customValidator.noWhiteSpace.NoWhiteSpace;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PatchUserReq {

	@Email
	private String email;

	@NoWhiteSpace
	private String name;

	@IntArrayRange(min = 0, max = 2, message = "role is invalid")
	private int[] roleOrdinals;
}
