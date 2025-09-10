package com.example.catalogueservice.repository;

import com.example.catalogueservice.entity.Order;
import com.example.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface OrderRepository extends CrudRepository<Order, UUID> {
    @Query(value = """
            FROM Order o WHERE
            (:transactionId IS NULL OR o.transactionId = :transactionId) AND
            (:createdAt IS NULL OR o.createdAt = :createdAt) AND
            (:status IS NULL OR o.status = :status) AND
            (:userId IS NULL OR o.userId = :userId)
            """)
    Page<Order> findAll(
            Pageable pageable,
            @Param("transactionId") UUID transactionId,
            @Param("createdAt") LocalDateTime createdAt,
            @Param("status") OrderStatus status,
            @Param("userId") UUID userId);
}
