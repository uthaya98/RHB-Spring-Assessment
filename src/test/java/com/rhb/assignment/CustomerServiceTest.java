package com.rhb.assignment;

import com.rhb.assignment.dto.CustomerRequest;
import com.rhb.assignment.dto.CustomerResponse;
import com.rhb.assignment.entity.Customer;
import com.rhb.assignment.exception.ResourceNotFoundException;
import com.rhb.assignment.repository.CustomerRepository;
import com.rhb.assignment.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {

        customer = new Customer();
        customer.setId(1L);
        customer.setName("John Tan");
        customer.setEmail("john@gmail.com");
        customer.setPhone("0123456789");
    }

    @Test
    void shouldCreateCustomer() {

        CustomerRequest request = new CustomerRequest(
                "John Tan",
                "john@gmail.com",
                "0123456789"
        );

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customer);

        CustomerResponse response =
                customerService.createCustomer(request);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("John Tan", response.name());
        assertEquals("john@gmail.com", response.email());

        verify(customerRepository, times(1))
                .save(any(Customer.class));
    }

    @Test
    void shouldGetCustomerById() {

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        CustomerResponse response =
                customerService.getCustomerById(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("John Tan", response.name());

        verify(customerRepository, times(1))
                .findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {

        when(customerRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> customerService.getCustomerById(99L)
        );

        verify(customerRepository, times(1))
                .findById(99L);
    }

}
