package com.bap.intern.shopee.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bap.intern.shopee.dto.category.PostAndPatchCategoryRes;
import com.bap.intern.shopee.entity.Category;
import com.bap.intern.shopee.exception.customException.CategoryExistedException;
import com.bap.intern.shopee.exception.customException.CategoryNotExistedException;
import com.bap.intern.shopee.repository.CategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	public PostAndPatchCategoryRes postCategory(String name) {
		Optional<Category> categoryOptional = categoryRepository.findByName(name);
		
		if (categoryOptional.isEmpty()) {
			Category newCategory = new Category();
			newCategory.setName(name);
			categoryRepository.save(newCategory);
			
			log.info("Add a new category successfully: " + name);
			return new PostAndPatchCategoryRes(newCategory, HttpStatus.CREATED.value());
		} else {
			throw new CategoryExistedException();
		}
	}
	
	public PostAndPatchCategoryRes patchCategory(int categoryId, String name) {
		Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
		
		if (categoryOptional.isPresent()) {
			Category category = categoryOptional.get();
			String oldName = category.getName();
			category.setName(name);
			categoryRepository.save(category);
			
			log.info("Update category successfully: from " + oldName + " to " + name);
			return new PostAndPatchCategoryRes(category, HttpStatus.OK.value());
		} else {
			throw new CategoryNotExistedException();
		}
	}
	
	public void deleteCategory(int categoryId) {
		Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
		
		if (categoryOptional.isPresent()) {
			String name = categoryOptional.get().getName();
			categoryRepository.delete(categoryOptional.get());
			
			log.info("delete category successfully: id: " + categoryId + " name: " +  name);
		} else {
			throw new CategoryNotExistedException();
		}
	}
}
