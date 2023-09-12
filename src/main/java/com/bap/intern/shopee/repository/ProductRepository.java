package com.bap.intern.shopee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bap.intern.shopee.entity.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
