package com.example.dto;

public record CheckVerificationResult(
        boolean isExists,
        Boolean isVerified) {
}
