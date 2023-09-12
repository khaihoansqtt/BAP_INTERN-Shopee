package com.bap.intern.shopee.dto.category;

import com.bap.intern.shopee.customValidator.noWhiteSpace.NoWhiteSpace;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PatchCategoryReq {
	
	@NotNull
	private int categoryId;
	
	@NoWhiteSpace
	private String name;
}
