package com.example.catalogueservice.mapper.impl;

import com.example.catalogueservice.entity.Order;
import com.example.catalogueservice.entity.Product;
import com.example.catalogueservice.mapper.OrderMapper;
import com.example.dto.OrderDto;
import com.example.dto.PageDto;
import com.example.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OrderMapperImpl implements OrderMapper {
    @Override
    public Order create(UUID userId, List<Product> products) {
        return Order.builder()
                .userId(userId)
                .products(products)
                .build();
    }

    @Override
    public OrderDto read(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .userId(order.getUserId())
                .transactionId(order.getTransactionId())
                .productIds(order.getProducts().stream().map(Product::getId).toList())
                .amount(order.getProducts().stream().map(Product::getPrice).mapToInt(Integer::intValue).sum())
                .build();
    }

    @Override
    public PageDto<OrderDto> readPage(Page<Order> page) {
        return new PageDto<>(
                page.stream().map(this::read).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.hasNext(),
                page.hasPrevious());
    }

    @Override
    public Order update(Order order, List<Product> products) {
        order.setProducts(products);

        return order;
    }

    @Override
    public Order pay(Order order) {
        order.setStatus(OrderStatus.IN_PROCESSING);

        return order;
    }
}
