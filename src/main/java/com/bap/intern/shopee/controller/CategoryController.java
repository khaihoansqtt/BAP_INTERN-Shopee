package com.bap.intern.shopee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bap.intern.shopee.dto.category.PatchCategoryReq;
import com.bap.intern.shopee.dto.category.PostAndPatchCategoryRes;
import com.bap.intern.shopee.dto.category.PostCategoryReq;
import com.bap.intern.shopee.service.CategoryService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@PostMapping
	public ResponseEntity<?> postCategory(@Valid @RequestBody PostCategoryReq req) {
		String categoryName = req.getName();
		PostAndPatchCategoryRes res = categoryService.postCategory(categoryName);
		return new ResponseEntity(res, HttpStatus.CREATED);
	}
	
	@PatchMapping
	public ResponseEntity<?> patchCategory(@Valid @RequestBody PatchCategoryReq req) {
		int categoryId = req.getCategoryId();
		String categoryName = req.getName();
		PostAndPatchCategoryRes res = categoryService.patchCategory(categoryId, categoryName);
		return new ResponseEntity(res, HttpStatus.OK);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") int categoryId) {
		categoryService.deleteCategory(categoryId);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}
