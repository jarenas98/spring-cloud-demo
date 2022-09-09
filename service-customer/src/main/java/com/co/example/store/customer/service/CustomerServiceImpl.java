package com.co.example.store.customer.service;

import com.co.example.store.customer.entity.Customer;
import com.co.example.store.customer.entity.Region;
import com.co.example.store.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findAllCustomers() {
        return this.customerRepository.findAll();
    }

    @Override
    public List<Customer> findCustomersByRegion(Region region) {
        return this.customerRepository.findByRegion(region).orElse(new ArrayList<>());
    }

    @Override
    public Customer createCustomer(Customer newCustomer) {

        Optional<Customer> foundCustomer = this.customerRepository.findByNumberID(newCustomer.getNumberID());
        if (foundCustomer.isPresent()) {
            return foundCustomer.get();
        }

        newCustomer.setState("CREATED");

        return this.customerRepository.save(newCustomer);

    }

    @Override
    public Customer updateCustomer(Customer customer) {

        Optional<Customer> optionalFoundCustomer = this.customerRepository.findByNumberID(customer.getNumberID());
        if (!optionalFoundCustomer.isPresent()) {
            log.error("Customer with id [{}] not found", customer.getId());
            return null;
        }
        Customer foundCustomer = optionalFoundCustomer.get();
        foundCustomer.setFirstName(customer.getFirstName());
        foundCustomer.setLastName(customer.getLastName());
        foundCustomer.setPhotoUrl(customer.getPhotoUrl());
        foundCustomer.setEmail(customer.getEmail());

        return this.customerRepository.save(foundCustomer);
    }

    @Override
    public Customer deleteCustomer(Customer customer) {
        Optional<Customer> optionalFoundCustomer = this.customerRepository.findById(customer.getId());
        if (!optionalFoundCustomer.isPresent()) {
            return null;
        }
        Customer foundCustomer = optionalFoundCustomer.get();
        foundCustomer.setState("DELETED");

        return this.customerRepository.save(foundCustomer);
    }

    @Override
    public Customer getCustomer(Long customerId) {
        return this.customerRepository.findById(customerId).orElse(null);
    }
}
