package com.co.example.store.shopping.service;

import com.co.example.store.shopping.client.CustomerClient;
import com.co.example.store.shopping.client.ProductClient;
import com.co.example.store.shopping.entity.Invoice;
import com.co.example.store.shopping.entity.InvoiceItem;
import com.co.example.store.shopping.model.Customer;
import com.co.example.store.shopping.model.Product;
import com.co.example.store.shopping.repository.InvoiceItemsRepository;
import com.co.example.store.shopping.repository.InvoiceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class InvoiceServiceImpl implements IInvoiceService {

    private InvoiceRepository invoiceRepository;

    private InvoiceItemsRepository invoiceItemsRepository;

    private CustomerClient customerClient;

    private ProductClient productClient;


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
        Invoice invoiceSaved = this.invoiceRepository.save(invoice);
        invoiceSaved.getItems().forEach(invoiceItem -> {
            this.productClient.updateStockProduct(invoiceItem.getProductId(), invoiceItem.getQuantity() * -1);
        });


        return invoiceSaved;
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
        Invoice invoice = this.invoiceRepository.findById(id).orElse(null);
        if (invoice != null) {
            Customer customer = this.customerClient.getCustomer(invoice.getCustomerId()).getBody();
            invoice.setCustomer(customer);
            List<InvoiceItem> itemList = invoice.getItems().stream()
                    .map(invoiceItem -> {
                        Product product = this.productClient.getProduct(invoiceItem.getProductId()).getBody();
                        invoiceItem.setProduct(product);
                        return invoiceItem;
                    }).collect(Collectors.toList());
            invoice.setItems(itemList);
        }

        return invoice;
    }
}
