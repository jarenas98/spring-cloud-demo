package com.co.example.store.shopping.repository;

import com.co.example.store.shopping.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    public Optional<List<Invoice>> findByCustomerId(Long customerId);

    public Optional<Invoice> findByNumberInvoice(String numberInvoice);
}
