package com.rhb.assignment.repository;

import com.rhb.assignment.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);

    @Query("""
            SELECT o FROM Order o
            JOIN o.customer c WHERE c.id = :customerId
            """)
    List<Order> findOrderByCustomer(@Param("customerId") Long customerId);
}
