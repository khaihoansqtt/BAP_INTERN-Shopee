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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bap.intern.shopee.dto.product.PatchProductReq;
import com.bap.intern.shopee.dto.product.PostAndPatchProductRes;
import com.bap.intern.shopee.dto.product.PostProductReq;
import com.bap.intern.shopee.service.ProductService;

import jakarta.validation.Valid;
 
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@PostMapping()
	public ResponseEntity<?> postProduct(@Valid @RequestBody PostProductReq req) {
		return new ResponseEntity(productService.postProduct(req), HttpStatus.CREATED);
	}
	
	@PostMapping("/from-excel")
	public ResponseEntity<?> importFromExcel(@RequestParam("excelFile") MultipartFile file) {
		productService.importFromExcel(file);
		return null;
	}
	
	@PatchMapping
	public ResponseEntity<?> patchProduct(@Valid @RequestBody PatchProductReq req) {
		PostAndPatchProductRes res = productService.patchProduct(req);
		return new ResponseEntity(res, HttpStatus.OK);
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable("productId") int productId) {
		productService.deleteProduct(productId);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}
