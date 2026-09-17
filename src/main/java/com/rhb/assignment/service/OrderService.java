package com.rhb.assignment.service;

import com.rhb.assignment.dto.OrderRequest;
import com.rhb.assignment.dto.OrderResponse;
import com.rhb.assignment.entity.Customer;
import com.rhb.assignment.entity.Order;
import com.rhb.assignment.exception.ResourceNotFoundException;
import com.rhb.assignment.repository.CustomerRepository;
import com.rhb.assignment.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public OrderResponse createOrder(OrderRequest request){
        Order order = new Order();

        Long id = request.customerId();

        Customer customer = customerRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        order.setProductName(request.productName());
        order.setAmount(request.amount());
        order.setStatus(request.status());
        order.setCustomer(customer);

        Order savedOrder = orderRepository.save(order);

        return mapToResponse(savedOrder);

    }

    public OrderResponse getOrderById(Long Id){
        Order order = orderRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + Id));

        return mapToResponse(order);
    }

    public List<OrderResponse> getOrderByCustomer(Long customerId){
        return orderRepository.findOrderByCustomer(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private OrderResponse mapToResponse(Order order) {

        return new OrderResponse(
                order.getId(),
                order.getProductName(),
                order.getAmount(),
                order.getStatus(),
                order.getCustomer().getId(),
                order.getCustomer().getName()
        );
    }
}
