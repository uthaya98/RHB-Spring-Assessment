package com.rhb.assignment.service;

import com.rhb.assignment.dto.CustomerRequest;
import com.rhb.assignment.dto.CustomerResponse;
import com.rhb.assignment.entity.Customer;
import com.rhb.assignment.exception.ResourceNotFoundException;
import com.rhb.assignment.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //ADD NEW CUSTOMER
    public CustomerResponse createCustomer(CustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());

        Customer savedCustomer = customerRepository.save(customer);

        return mapToResponse(savedCustomer);
    }

    //GET CUSTOMER
    public CustomerResponse getCustomerById(Long Id){
        Customer customer = customerRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + Id));

        return mapToResponse(customer);
    }

    //UPDATE EXISTING CUSTOMER
    public CustomerResponse updateCustomer(Long Id, CustomerRequest request){
        Customer customer = customerRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + Id));

        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());

        Customer updatedCustomer = customerRepository.save(customer);

        return mapToResponse(updatedCustomer);
    }

    //DELETE THE CUSTOMER
    public void deleteCustomer(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with id: " + id));

        customerRepository.delete(customer);
    }

    public Page<CustomerResponse> searchCustomer(String name, Pageable pageable){
        return customerRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(this::mapToResponse);
    }

    private CustomerResponse mapToResponse(Customer customer){
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone()
        );
    }
}
