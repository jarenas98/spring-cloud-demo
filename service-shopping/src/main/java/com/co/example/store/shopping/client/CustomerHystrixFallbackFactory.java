package com.co.example.store.shopping.client;

import com.co.example.store.shopping.model.Customer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Configuration
public class CustomerHystrixFallbackFactory implements CustomerClient {
    @Override
    public ResponseEntity<Customer> getCustomer(Long customerId) {
        Customer customer = Customer.builder()
                .firstName("none")
                .lastName("none")
                .email("none")
                .photoUrl("none")
                .build();

        return ResponseEntity.ok(customer);
    }
}
