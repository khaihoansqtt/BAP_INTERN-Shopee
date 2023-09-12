package com.bap.intern.shopee.dto.category;

import com.bap.intern.shopee.entity.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRes {
	private int id;
	private String name;
	public CategoryRes(Category category) {
		name = category.getName();
		id = category.getId();
	}
}
