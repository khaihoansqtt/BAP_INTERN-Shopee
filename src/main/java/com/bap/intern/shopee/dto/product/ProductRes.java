package com.bap.intern.shopee.dto.product;

import com.bap.intern.shopee.entity.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRes {
	private int id;
	private String name;
	private double price;
	public ProductRes(Product product) {
		id = product.getId();
		name = product.getName();
		price = product.getPrice();
	}
}
