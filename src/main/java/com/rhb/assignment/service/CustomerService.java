package com.rhb.assignment.service;

import com.rhb.assignment.dto.CustomerRequest;
import com.rhb.assignment.dto.CustomerResponse;
import com.rhb.assignment.entity.Customer;
import com.rhb.assignment.exception.ResourceNotFoundException;
import com.rhb.assignment.exception.ValidationException;
import com.rhb.assignment.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private int counter = 0;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //ADD NEW CUSTOMER
    public CustomerResponse createCustomer(CustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name());
        validateEmail(request.email());
        customer.setEmail(request.email());
        validatePhoneNumber(request.phone());
        customer.setPhone(request.phone());

        Customer savedCustomer = customerRepository.save(customer);

        List<Customer> resList = customerRepository.findAll().stream().toList();

        if(resList.size() > 0){
            counter = resList.size();
        } else{
            counter++;
        }

        System.out.println(counter);
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
        validateEmail(request.email());
        customer.setEmail(request.email());
        validatePhoneNumber(request.phone());
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

    private void validatePhoneNumber(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new ValidationException("Phone number is required");
        }

        // Malaysian mobile number:
        // Starts with 01 and contains 9-10 digits in total
        if (!phone.matches("^01\\d{8,9}$")) {
            throw new ValidationException("Please enter a valid phone number");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email is required");
        }

        String emailRegex =
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (!email.matches(emailRegex)) {
            throw new ValidationException("Please enter a valid email address");
        }
    }
}
