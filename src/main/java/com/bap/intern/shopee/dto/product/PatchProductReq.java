package com.bap.intern.shopee.dto.product;

import com.bap.intern.shopee.customValidator.noWhiteSpace.NoWhiteSpace;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PatchProductReq {
	
	@NotNull
	private int productId;
	
	@NoWhiteSpace
	private String name;
	
	@Min(value = 0)
	private Double price;
	
	private int categoryId;
}
