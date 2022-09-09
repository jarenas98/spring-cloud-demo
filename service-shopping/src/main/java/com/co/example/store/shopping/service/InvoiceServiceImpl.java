package com.co.example.store.shopping.service;

import com.co.example.store.shopping.entity.Invoice;
import com.co.example.store.shopping.repository.InvoiceItemsRepository;
import com.co.example.store.shopping.repository.InvoiceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class InvoiceServiceImpl implements IInvoiceService {

    private InvoiceRepository invoiceRepository;

    private InvoiceItemsRepository invoiceItemsRepository;


    @Override
    public List<Invoice> findInvoiceAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        Optional<Invoice> invoiceDB = this.invoiceRepository.findByNumberInvoice(invoice.getNumberInvoice());
        if (invoiceDB.isPresent()) {
            return invoiceDB.get();
        }
        invoice.setState("CREATED");

        return this.invoiceRepository.save(invoice);
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null) {
            return null;
        }
        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setDescription(invoice.getDescription());
        invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
        invoiceDB.getItems().clear();
        invoiceDB.setItems(invoice.getItems());

        return invoiceRepository.save(invoiceDB);
    }

    @Override
    public Invoice deleteInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null) {
            return null;
        }
        invoiceDB.setState("DELETED");

        return this.invoiceRepository.save(invoiceDB);
    }

    @Override
    public Invoice getInvoice(Long id) {
        return this.invoiceRepository.findById(id).orElse(null);
    }
}
