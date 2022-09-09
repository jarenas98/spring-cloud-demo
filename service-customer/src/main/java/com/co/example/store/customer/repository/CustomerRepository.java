package com.co.example.store.customer.repository;

import com.co.example.store.customer.entity.Customer;
import com.co.example.store.customer.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Optional<Customer> findByNumberID(String numberID);
    public Optional<List<Customer>> findByLastName(String lastName);
    public Optional<List<Customer>> findByRegion(Region region);
}
