package com.co.example.store.shopping.service;

import com.co.example.store.shopping.entity.Invoice;

import java.util.List;

public interface IInvoiceService {
    public List<Invoice> findInvoiceAll();

    public Invoice createInvoice(Invoice invoice);

    public Invoice updateInvoice(Invoice invoice);

    public Invoice deleteInvoice(Invoice invoice);

    public Invoice getInvoice(Long id);
}
