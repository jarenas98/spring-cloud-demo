package com.co.example.store.shopping.controller;

import com.co.example.store.shopping.entity.Invoice;
import com.co.example.store.shopping.service.IInvoiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private IInvoiceService invoiceService;

    @GetMapping()
    public ResponseEntity<List<Invoice>> listAllInvoices() {
        List<Invoice> invoices = this.invoiceService.findInvoiceAll();
        if (invoices.isEmpty()) {

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(invoices);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable("id") Long id) {
        log.info("Fetching Invoice with id {}", id);
        Invoice invoice = this.invoiceService.getInvoice(id);
        if (invoice == null) {
            log.error("Invoice with id {} not found.", id);

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(invoice);
    }

    @PostMapping()
    public ResponseEntity<Invoice> createInvoice(@Valid @RequestBody Invoice invoice, BindingResult result) {
        log.info("Creating Invoice : {}", invoice);
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        Invoice invoiceDB = this.invoiceService.createInvoice(invoice);

        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable("id") Long id, @Valid @RequestBody Invoice invoice, BindingResult result) {
        log.info("Updating Invoice with id {}", id);
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        invoice.setId(id);
        Invoice currentInvoice = this.invoiceService.updateInvoice(invoice);

        if (currentInvoice == null) {
            log.error("Unable to update. Invoice with id {} not found.", id);

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(currentInvoice);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Invoice> deleteInvoice(@PathVariable("id") Long id) {
        log.info("Fetching & Deleting Invoice with id {}", id);

        Invoice invoice = Invoice.builder().id(id).build();
        invoice = this.invoiceService.deleteInvoice(invoice);

        if (invoice == null) {
            log.error("Unable to delete. Invoice with id {} not found.", id);

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(invoice);
    }

    private String formatMessage(BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors().stream().map(err -> {
            Map<String, String> error = new HashMap<>();
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
            throw new RuntimeException(e);
        }

        return jsonString;
    }

}
