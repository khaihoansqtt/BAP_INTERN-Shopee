package com.bap.intern.shopee.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bap.intern.shopee.dto.product.PatchProductReq;
import com.bap.intern.shopee.dto.product.PostAndPatchProductRes;
import com.bap.intern.shopee.dto.product.PostProductReq;
import com.bap.intern.shopee.entity.Category;
import com.bap.intern.shopee.entity.Product;
import com.bap.intern.shopee.exception.customException.CategoryNotExistedException;
import com.bap.intern.shopee.exception.customException.ProductNotExistedException;
import com.bap.intern.shopee.exception.customException.ImportProductFromFileException;
import com.bap.intern.shopee.repository.CategoryRepository;
import com.bap.intern.shopee.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	CategoryRepository categoryRepository;

	public PostAndPatchProductRes postProduct(PostProductReq req) {
		Optional<Category> categoryOptional = categoryRepository.findById(req.getCategoryId());

		if (categoryOptional.isPresent()) {
			Product newProduct = new Product();
			newProduct.setName(req.getName());
			newProduct.setPrice(req.getPrice());
			newProduct.setCategory(categoryOptional.get());

			productRepository.save(newProduct);
			log.info("Add product successfully");
			return new PostAndPatchProductRes(newProduct, HttpStatus.CREATED.value());
		} else {
			throw new CategoryNotExistedException();
		}
	}

	public PostAndPatchProductRes patchProduct(PatchProductReq req) {
		Optional<Product> productOptional = productRepository.findById(req.getProductId());

		if (productOptional.isPresent()) {
			Optional<Category> categoryOptional = categoryRepository.findById(req.getCategoryId());

			if (categoryOptional.isPresent()) {
				Product product = productOptional.get();
				if (req.getName() != null) product.setName(req.getName());
				if (req.getPrice() != null) product.setPrice(req.getPrice());

				productRepository.save(product);
				log.info("update product successfully");
				return new PostAndPatchProductRes(product, HttpStatus.OK.value());
			} else {
				throw new CategoryNotExistedException();
			}
		} else {
			throw new ProductNotExistedException();
		}
	}

	public void deleteProduct(int productId) {
		Optional<Product> productOptional = productRepository.findById(productId);

		if (productOptional.isPresent()) {
			productRepository.delete(productOptional.get());
			log.info("delete product successfully, id = " + productId);
		} else {
			throw new ProductNotExistedException();
		}
	}

	public void importFromExcel(MultipartFile file)  {
        InputStream inputStream;
        Workbook workbook;
		try {
			inputStream = file.getInputStream();
			workbook = new XSSFWorkbook(inputStream);
	        Sheet sheet = workbook.getSheetAt(0);
	        productSheetToDb(sheet);
	        
	        workbook.close();
	        inputStream.close();
		} catch (IOException e) {
			throw new ImportProductFromFileException(e.getMessage());
		}
	}
	
	public void productSheetToDb(Sheet sheet) {
		log.info("begin import products from file");
        ExecutorService executor = Executors.newFixedThreadPool(30);
        // Lưu lần lượt các category vào map để khỏi truy vấn db nhiều lần trong vòng lặp
        Map<Integer, Category> categories = new HashMap<>();
        for (Row row : sheet) {
        	executor.execute(() -> {
                String name = row.getCell(0).getStringCellValue();
                double price = (double) row.getCell(1).getNumericCellValue();
                int categoryId = (int) row.getCell(2).getNumericCellValue();
                System.out.println("name " + name);
                System.out.println("price " + price);
                System.out.println("categoryId " + categoryId);
                
                Category category;
                if (categories.containsKey(categoryId)) {
                	category = categories.get(categoryId);
                } else {
                	category = categoryRepository.findById(categoryId).get();
                }
                
                Product product = Product.builder().name(name).price(price).category(category).build();
                productRepository.save(product);
        	});
        }
        executor.shutdown();
	}

	@Transactional
	public Object testFlush(PostProductReq req) throws InterruptedException {
		Category category = categoryRepository.findById(req.getCategoryId()).orElse(new Category());
		Product product = Product.builder().name(req.getName()).price(req.getPrice()).category(category).build();
		productRepository.save(product);
//		Thread.sleep(3000);
		return null;
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public Object testFlush2(PostProductReq req) throws InterruptedException {
		List<Product> products = productRepository.findAllProducts();
		log.info("--------lan 1: products: {}", products.size());
		Thread.sleep(3000);
		List<Product> products2 = productRepository.findAllProducts();
		log.info("--------lan 2: products: {}", products2.size());
		return products;

	}

	@Transactional
	public Object testJpa() throws InterruptedException {
		Product product = productRepository.findById(1).orElse(new Product());
		product.setName("mid-step-rename");
		productRepository.saveAndFlush(product);
		Thread.sleep(3000);
		return product;
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public Object testJpa2() {
		List<Product> products = productRepository.findAllProducts();
		log.info("-------2: products: {}", products);
		return products;
	}


}
