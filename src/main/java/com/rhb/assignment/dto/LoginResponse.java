package com.rhb.assignment.dto;

public record LoginResponse(
        String token,
        String type,
        long expiresIn
) {
}
