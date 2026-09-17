package com.rhb.assignment.controller;

import com.rhb.assignment.dto.CustomerRequest;
import com.rhb.assignment.dto.CustomerResponse;
import com.rhb.assignment.entity.Customer;
import com.rhb.assignment.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Customer API",
        description = "Customer management operations"
)
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // POST - Create customer
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request){

        CustomerResponse response = customerService.createCustomer(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //GET - Get a Customer
    @Operation(
            summary = "Get customer by ID",
            description = "Returns a customer using the customer ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long id){
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    //PUT - Update existing Customer
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequest request){
        return ResponseEntity.ok(customerService.updateCustomer(id, request));
    }

    //DELETE - Delete Customer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }

    // GET - Search + Pagination
    @GetMapping("/search")
    public ResponseEntity<Page<CustomerResponse>> searchCustomers( @RequestParam(defaultValue = "") String name,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(customerService.searchCustomer(name, pageable));
    }
}
