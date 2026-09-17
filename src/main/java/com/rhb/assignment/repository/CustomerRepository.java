package com.rhb.assignment.repository;

import com.rhb.assignment.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
