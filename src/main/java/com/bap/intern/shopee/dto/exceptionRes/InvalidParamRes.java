package com.bap.intern.shopee.dto.exceptionRes;

import java.util.Map;

import com.bap.intern.shopee.dto.BaseRes;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InvalidParamRes extends BaseRes{
	private Map<String, String> errorDetails;

	public InvalidParamRes(Map<String, String> errorDetails) {
		super();
		status = 400;
		message = "Invalid parameters";
		this.errorDetails = errorDetails;
	}
}
