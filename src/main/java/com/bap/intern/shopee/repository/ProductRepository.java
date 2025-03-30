package com.bap.intern.shopee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bap.intern.shopee.entity.Product;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(nativeQuery = true, value = "select * from products")
    List<Product> findAllProducts();

}
