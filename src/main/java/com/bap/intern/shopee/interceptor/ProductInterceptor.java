package com.bap.intern.shopee.interceptor;

import com.bap.intern.shopee.entity.Category;
import com.bap.intern.shopee.entity.Product;
import com.bap.intern.shopee.repository.CategoryRepository;
import com.bap.intern.shopee.util.BeanUtils;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class ProductInterceptor {

    private CategoryRepository categoryRepository;

    @PrePersist
    @PreRemove
    @PreUpdate
    private void beforeAnyUpdate(Product product) {
        if (product.getId() == 0) {
            log.info("[USER AUDIT] About to add a product");
        } else {
            log.info("[USER AUDIT] About to update/delete product: " + product.getId());
        }
    }

    @PostPersist
    private void afterAnyUpdate(Product product) {
        log.info("[USER AUDIT] add complete for user: " + product.getId());
        Category category = product.getCategory();
        category.setName("after interceptor");
        getCategoryRepository().save(category);
    }

    @PostLoad
    private void afterLoad(Product product) {

        log.info("[USER AUDIT] user loaded from database: " + product.getId());
    }

    private CategoryRepository getCategoryRepository() {
        if (Objects.isNull(categoryRepository)) {
            categoryRepository = BeanUtils.getBean(CategoryRepository.class);
        }
        return categoryRepository;
    }
}