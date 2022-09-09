package com.co.example.store.product.repository;

import com.co.example.store.product.entity.Category;
import com.co.example.store.product.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ProductRepositoryMockTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFindByCategory_thenReturnListProduct() {
        Product product01= Product.builder()
                .name("computer")
                .category(Category.builder().id(1L).build())
                .description("")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("1230.09"))
                .status("Created")
                .createdAt(new Date()).build();

       this.productRepository.save(product01);

        List<Product> products = this.productRepository.findByCategory(Category.builder().id(1L).build()).get();

        assertTrue(products.size()==3);


    }

}