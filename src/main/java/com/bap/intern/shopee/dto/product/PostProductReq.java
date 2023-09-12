package com.bap.intern.shopee.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostProductReq {

	@NotBlank(message = "name must not be blank")
	private String name;

	@NotNull
	@Min(value = 0)
	private double price;

	@NotNull
	private int categoryId;
}
