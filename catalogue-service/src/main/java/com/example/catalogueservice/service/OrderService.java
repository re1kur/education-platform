package com.example.catalogueservice.service;

import com.example.dto.OrderDto;
import com.example.dto.PageDto;
import com.example.filter.OrderFilter;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public interface OrderService {
    OrderDto create(Jwt jwt, UUID[] productIds);

    PageDto<OrderDto> readAll(int page, int size, OrderFilter filter);

    OrderDto read(UUID id);
}
