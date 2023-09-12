package com.bap.intern.shopee.dto.category;

import com.bap.intern.shopee.dto.CommonRes;
import com.bap.intern.shopee.entity.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostAndPatchCategoryRes extends CommonRes {
	
	private CategoryRes categoryRes;

	public PostAndPatchCategoryRes(Category category, int status) {
		super();
		this.status = status;
		message = "Create/update category successfully";
		categoryRes = new CategoryRes(category);
	}
}
