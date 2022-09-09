package com.co.example.store.product.services;

import com.co.example.store.product.entity.Category;
import com.co.example.store.product.entity.Product;
import com.co.example.store.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImp implements IProductService {

    private ProductRepository productRepository;

    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> listAllProduct() {
        return this.productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return this.productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        product.setStatus("CREATED");
        product.setCreatedAt(new Date());
        return this.productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product productDb = getProduct(product.getId());
        if(null == productDb) {
            return null;
        }
        productDb.setName(product.getName());
        productDb.setDescription(product.getDescription());
        productDb.setCategory(product.getCategory());
        productDb.setPrice(product.getPrice());

        return this.productRepository.save(productDb);
    }

    @Override
    public Product deleteProduct(Long id) {
        Product productDb = getProduct(id);
        if(null == productDb) {
            return null;
        }

        productDb.setStatus("Deleted");

        return this.productRepository.save(productDb);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return this.productRepository.findByCategory(category).orElse(new ArrayList<>());
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        Product productDb = getProduct(id);
        if(null == productDb) {
            return null;
        }
        Double stock = productDb.getStock() +quantity;
        productDb.setStock(stock);

        return this.productRepository.save(productDb);
    }
}
