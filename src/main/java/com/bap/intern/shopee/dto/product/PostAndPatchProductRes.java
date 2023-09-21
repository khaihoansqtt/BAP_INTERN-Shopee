package com.bap.intern.shopee.dto.product;

import com.bap.intern.shopee.dto.BaseRes;
import com.bap.intern.shopee.entity.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostAndPatchProductRes extends BaseRes {
	
	private ProductRes productRes;

	public PostAndPatchProductRes(Product product, int status) {
		super();
		this.status = status;
		message = "Create/update product successfully";
		productRes = new ProductRes(product);
	}
}
