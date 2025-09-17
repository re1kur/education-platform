package com.example.catalogueservice.entity;

import com.example.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.FetchType.EAGER;

@Entity
@Table(name = "orders")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID userId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private UUID transactionId;

    private LocalDateTime createdAt;

    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (!(o instanceof Order order)) return false;

        if (id == null || order.id == null) {
            return false;
        }

        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return id == null ? System.identityHashCode(this) : id.hashCode();
    }

    @PrePersist
    private void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (status == null) status = OrderStatus.NEW;
    }
}
