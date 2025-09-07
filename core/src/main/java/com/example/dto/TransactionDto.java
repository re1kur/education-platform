package com.example.dto;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDto(String id,
                             String userId,
                             String orderId,
                             String type,
                             String status,
                             BigDecimal amount,
                             LocalDateTime date,
                             String description) {
}
