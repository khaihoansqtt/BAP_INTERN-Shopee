package com.bap.intern.shopee.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bap.intern.shopee.dto.category.PostAndPatchCategoryRes;
import com.bap.intern.shopee.entity.Category;
import com.bap.intern.shopee.exception.customException.CategoryException;
import com.bap.intern.shopee.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	public PostAndPatchCategoryRes postCategory(String name) {
		Optional<Category> categoryOptional = categoryRepository.findByName(name);
		
		if (categoryOptional.isEmpty()) {
			Category newCategory = new Category();
			newCategory.setName(name);
			categoryRepository.save(newCategory);
			
			return new PostAndPatchCategoryRes(newCategory, HttpStatus.CREATED.value());
		} else {
			throw new CategoryException("category name is existed");
		}
	}
	
	public PostAndPatchCategoryRes patchCategory(int categoryId, String name) {
		Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
		
		if (categoryOptional.isPresent()) {
			Category category = categoryOptional.get();
			category.setName(name);
			categoryRepository.save(category);
			
			return new PostAndPatchCategoryRes(category, HttpStatus.OK.value());
		} else {
			throw new CategoryException("category is not existed in the system");
		}
	}
	
	public void deleteCategory(int categoryId) {
		Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
		
		if (categoryOptional.isPresent()) {
			categoryRepository.delete(categoryOptional.get());
		} else {
			throw new CategoryException("category is not existed in the system");
		}
	}
}
