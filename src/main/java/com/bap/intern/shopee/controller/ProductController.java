package com.bap.intern.shopee.controller;

import com.bap.intern.shopee.dto.product.PatchProductReq;
import com.bap.intern.shopee.dto.product.PostAndPatchProductRes;
import com.bap.intern.shopee.dto.product.PostProductReq;
import com.bap.intern.shopee.entity.Category;
import com.bap.intern.shopee.entity.Product;
import com.bap.intern.shopee.repository.CategoryRepository;
import com.bap.intern.shopee.repository.ProductRepository;
import com.bap.intern.shopee.service.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
 
@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
	private final ProductRepository productRepository;
	private final ProductService productService;
	private final CategoryRepository categoryRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private SessionFactory sessionFactory;

	@GetMapping("/{id}")
	@Cacheable(value = "products", key = "#id")
	public Object getProduct(@PathVariable Integer id) {
		var a =  productRepository.findById(id);
		return a;
	}
	
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

	@PostMapping("/test-flush")
	public Object testFlush(@RequestBody PostProductReq req) throws InterruptedException {
		return productService.testFlush(req);
//		return null;
	}

	@PostMapping("/test-flush2")
	public Object testFlush2(@RequestBody PostProductReq req) throws InterruptedException {
		return productService.testFlush2(req);
//		return null;
	}

	@PostMapping("/test-jpa")
	public Object testJpa() throws InterruptedException {
		return productService.testJpa();
//		return null;
	}

	@PostMapping("/test-jpa2")
	public Object testJpa2() {
		return productService.testJpa2();
//		return null;
	}

	@PostMapping("/test-interceptor")
	@Transactional
	public String testInterceptor() {
		Category category = categoryRepository.findById(1).get();
		Product product = Product.builder().name("interceptor").price(1000).category(category).build();

		Session session = entityManager.unwrap(Session.class);
		System.out.println(session);
		session.setHibernateFlushMode(FlushMode.MANUAL);

		productRepository.save(product);
		return "sucess";
	}

}
