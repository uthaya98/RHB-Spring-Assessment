package com.rhb.assignment.dto;

import java.math.BigDecimal;

public record OrderResponse(
        Long id,
        String productName,
        BigDecimal amount,
        String status,
        Long customerId,
        String customerName
) {
}
