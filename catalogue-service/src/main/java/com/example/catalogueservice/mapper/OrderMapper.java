package com.example.catalogueservice.mapper;

import com.example.catalogueservice.entity.Order;
import com.example.catalogueservice.entity.Product;
import com.example.dto.OrderDto;
import com.example.dto.PageDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface OrderMapper {
    Order create(UUID userId, List<Product> products);

    OrderDto read(Order order);

    PageDto<OrderDto> readPage(Page<Order> ordersPage);
}
