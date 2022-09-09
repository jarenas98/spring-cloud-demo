package com.co.example.store.customer.service;

import com.co.example.store.customer.entity.Customer;
import com.co.example.store.customer.entity.Region;

import java.util.List;

public interface ICustomerService {

    public List<Customer> findAllCustomers();
    public List<Customer> findCustomersByRegion(Region region);
    public Customer createCustomer(Customer newCustomer);
    public Customer updateCustomer(Customer customer);
    public Customer deleteCustomer(Customer customer);
    public Customer getCustomer(Long customerId);
}
