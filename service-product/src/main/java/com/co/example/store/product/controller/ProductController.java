package com.co.example.store.product.controller;

import com.co.example.store.product.entity.Category;
import com.co.example.store.product.entity.Product;
import com.co.example.store.product.services.IProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.tomcat.jni.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/products")
@AllArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping()
    public ResponseEntity<List<Product>> listProduct(@RequestParam(name = "categoryId", required = false) Long categoryId) {
        List<Product> products = new ArrayList<>();
        if (categoryId == null) {
            products = this.productService.listAllProduct();
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        } else {
            products = this.productService.findByCategory(Category.builder()
                    .id(categoryId).build());
            if (products.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/{idProduct}")
    public ResponseEntity<Product> getProduct(@PathVariable("idProduct") Long id) {
        Product product = this.productService.getProduct(id);
        if (null == product) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result) {
        if(result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Product productCreate = this.productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
    }

    @PutMapping(value = "/{idProduct}")
    public ResponseEntity<Product> updateProduct(@PathVariable("idProduct") Long id, @RequestBody Product product) {
        product.setId(id);
        Product productDB = this.productService.updateProduct(product);
        if (productDB == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productDB);
    }

    @DeleteMapping("/{idProduct}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("idProduct") Long id) {

        Product productDelete = this.productService.deleteProduct(id);
        if(productDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(productDelete);
    }

    @PutMapping(value = "/{idProduct}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable("idProduct") Long id, @RequestParam("quantity") Double quantity){
        Product product = this.productService.updateStock(id, quantity);
        if(null == product) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }

    private String formatMessage(BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String,String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());

        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

}
