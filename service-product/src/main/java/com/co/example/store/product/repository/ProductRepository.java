package com.co.example.store.product.repository;

import com.co.example.store.product.entity.Category;
import com.co.example.store.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public Optional<List<Product>> findByCategory(Category category);
}
