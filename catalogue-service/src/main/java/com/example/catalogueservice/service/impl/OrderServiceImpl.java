package com.example.catalogueservice.service.impl;

import com.example.catalogueservice.entity.Order;
import com.example.catalogueservice.entity.Product;
import com.example.catalogueservice.mapper.OrderMapper;
import com.example.catalogueservice.outbox.OutboxEventService;
import com.example.catalogueservice.repository.OrderRepository;
import com.example.catalogueservice.service.OrderService;
import com.example.catalogueservice.service.ProductService;
import com.example.dto.OrderDto;
import com.example.dto.PageDto;
import com.example.enums.OrderStatus;
import com.example.enums.OutboxType;
import com.example.exception.OrderNotFoundException;
import com.example.filter.OrderFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repo;
    private final OrderMapper mapper;
    private final ProductService productService;
    private final OutboxEventService outboxService;

    private final String ORDER_NOT_FOUND_MESSAGE = "ORDER [%s] WAS NOT FOUND.";

    @Override
    @Transactional
    public OrderDto create(Jwt jwt, List<UUID> productIds) {
        UUID userId = UUID.fromString(jwt.getSubject());
        log.info("CREATE ORDER [{}] REQUEST BU USER [{}].", productIds, userId);

        List<Product> products = new ArrayList<>();

        for (UUID productId : productIds) {
            Product product = productService.get(productId, userId);
            products.add(product);
        }

        Order mapped = mapper.create(userId, products);
        Order saved = repo.save(mapped);

        log.info("CREATED ORDER [{}] BY USER [{}].", saved.getId(), userId);

        return mapper.read(saved);
    }

    @Override
    public PageDto<OrderDto> readAll(int page, int size, OrderFilter filter) {
        Pageable pageable = PageRequest.of(page, size);

        UUID transactionId = filter.transactionId();
        LocalDateTime createdAt = filter.createdAt();
        OrderStatus status = filter.status();
        UUID userId = filter.userId();

        Page<Order> ordersPage;
        if (createdAt != null) {
            ordersPage = repo.findAll(pageable, transactionId, createdAt, status, userId);
        } else ordersPage = repo.findAll(pageable, transactionId, status, userId);

        return mapper.readPage(ordersPage);
    }

    @Override
    public OrderDto read(UUID id) {
        return repo.findById(id).map(mapper::read)
                .orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND_MESSAGE.formatted(id)));
    }

    @Override
    public OrderDto update(UUID id, List<UUID> productIds, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        log.info("UPDATE ORDER [{}] REQUEST BY USER [{}]", id, userId);

        List<Product> products = new ArrayList<>();
        for (UUID productId : productIds) {
            Product product = productService.get(productId, userId);
            products.add(product);
        }

        Order order = repo.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND_MESSAGE.formatted(id)));

        Order mapped = mapper.update(order, products);
        Order saved = repo.save(mapped);

        log.info("UPDATED ORDER [{}] BY USER [{}].", saved.getId(), userId);
        return mapper.read(saved);
    }

    @Override
    @Transactional
    public void delete(UUID id, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        log.info("DELETE ORDER [{}] REQUEST BY USER [{}]", id, userId);

        Order order = repo.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND_MESSAGE.formatted(id)));

        repo.delete(order);

        log.info("ORDER [{}] IS DELETED BU USER [{}].", id, userId);
    }

    @Override
    public PageDto<OrderDto> readAllByUser(int page, int size, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        Pageable pageable = PageRequest.of(page, size);

        Page<Order> orders = repo.findAllByUserId(pageable, userId);

        return mapper.readPage(orders);
    }

    @Override
    @Transactional
    public void pay(UUID id, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        log.info("CREATE PAY ORDER [{}] REQUEST BY USER [{}]", id, userId);

        Order order = repo.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND_MESSAGE.formatted(id)));

        outboxService.createOrder(order);

        log.info("PAY ORDER [{}] REQUEST BY USER [{}] IS CREATED.", id, userId);
    }
}
