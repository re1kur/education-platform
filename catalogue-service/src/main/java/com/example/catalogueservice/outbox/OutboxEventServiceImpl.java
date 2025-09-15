package com.example.catalogueservice.outbox;

import com.example.catalogueservice.entity.Order;
import com.example.dto.OutboxEventDto;
import com.example.enums.OutboxType;
import com.example.event.PayOrderRequest;
import com.example.exception.OutboxEventNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxEventServiceImpl implements OutboxEventService {
    private final OutboxEventMapper mapper;
    private final OutboxEventRepository repo;

    @Override
    public void createOrder(Order order) {
        log.info("CREATE OUTBOX EVENT [{}]", OutboxType.PAY_ORDER_REQUEST);

        PayOrderRequest request = mapper.payOrder(order);

        OutboxEvent mapped = mapper.create(request, OutboxType.PAY_ORDER_REQUEST);
        OutboxEvent saved = repo.save(mapped);

        log.info("{} OUTBOX EVENT [{}] IS CREATED.", OutboxType.PAY_ORDER_REQUEST, saved.getId());
    }

    @Override
    public List<OutboxEventDto> readAll(OutboxType type) {
        log.info("READ ALL OUTBOX EVENTS BY TYPE [{}]", type);

        return repo.findAll(type).stream().map(mapper::read).toList();
    }

    @Override
    @Transactional
    public void reserve(UUID id) {
        log.info("RESERVE OUTBOX EVENT [{}] REQUEST.", id);

        OutboxEvent found = repo.findById(id)
                .orElseThrow(() -> new OutboxEventNotFoundException("OUTBOX EVENT [%s] WAS NOT FOUND.".formatted(id)));
        OutboxEvent mapped = mapper.reserve(found);

        OutboxEvent saved = repo.save(mapped);

        log.info("OUTBOX EVENT [{}] IS RESERVED TO [{}]", saved.getId(), saved.getReservedTo());
    }

    @Override
    public void release(UUID id) {
        log.info("RELEASE OUTBOX EVENT [{}] REQUEST.", id);

        OutboxEvent found = repo.findById(id)
                .orElseThrow(() -> new OutboxEventNotFoundException("OUTBOX EVENT [%s] WAS NOT FOUND.".formatted(id)));

        OutboxEvent mapped = mapper.release(found);
        OutboxEvent saved = repo.save(mapped);

        log.info("OUTBOX EVENT [{}] IS RELEASED.", saved.getId());
    }
}
