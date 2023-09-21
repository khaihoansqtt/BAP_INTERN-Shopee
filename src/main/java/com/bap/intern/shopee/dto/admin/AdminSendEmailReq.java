package com.bap.intern.shopee.dto.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminSendEmailReq {
	
	@NotBlank
	private String subject;
	
	@NotBlank
	private String text;
}
