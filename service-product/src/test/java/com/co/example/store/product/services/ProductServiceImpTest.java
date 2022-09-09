package com.co.example.store.product.services;

import com.co.example.store.product.entity.Category;
import com.co.example.store.product.entity.Product;
import com.co.example.store.product.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;
import org.assertj.core.api.Assertions.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class ProductServiceImpTest {

    @Mock
    private ProductRepository productRepository;

    @Autowired
    private IProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImp(this.productRepository);
        Product computer= Product.builder()
                .id(1L)
                .name("computer")
                .category(Category.builder().id(1L).build())
                .description("")
                .stock(Double.parseDouble("5"))
                .price(Double.parseDouble("12.09"))
                .createdAt(new Date()).build();

        Mockito.when(this.productRepository.findById(computer.getId()))
                .thenReturn(Optional.of(computer));

        Mockito.when(this.productRepository.save(computer))
                .thenReturn(computer);
    }

    @Test
    public void whenValidGetId_ThenReturnProduct() {
        Product found = this.productService.getProduct(1L);
        assertThat(found.getName()).isEqualTo("computer");
    }

    @Test
    public void whenValidUpdateStock_ThenReturnNewStock() {
        Product newStock = this.productService.updateStock(1L, Double.parseDouble("8"));
        assertThat(newStock.getStock()).isEqualTo(13);
    }

}