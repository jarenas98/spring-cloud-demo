package com.co.example.store.shopping.client;

import com.co.example.store.shopping.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "service-product")
public interface ProductClient {

    @GetMapping(value = "/products/{idProduct}")
    public ResponseEntity<Product> getProduct(@PathVariable("idProduct") Long id);

    @PutMapping(value = "/products/{idProduct}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable("idProduct") Long id, @RequestParam("quantity") Double quantity);
}
