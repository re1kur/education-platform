package com.example.catalogueservice.service;

import com.example.dto.OrderDto;
import com.example.dto.PageDto;
import com.example.filter.OrderFilter;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDto create(Jwt jwt, List<UUID> productIds);

    PageDto<OrderDto> readAll(int page, int size, OrderFilter filter);

    OrderDto read(UUID id);

    OrderDto update(UUID id, List<UUID> productIds, Jwt jwt);

    void delete(UUID id, Jwt jwt);

    PageDto<OrderDto> readAllByUser(int page, int size, Jwt jwt);

    void pay(UUID id, Jwt jwt);
}
