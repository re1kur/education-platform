package com.example.balanceservice.entity;

import com.example.enums.TransactionReferenceType;
import com.example.enums.TransactionStatus;
import com.example.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "transactions")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    private LocalDateTime createdAt;

    private LocalDateTime executedAt;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private Integer amount;

    @Enumerated(EnumType.STRING)
    private TransactionReferenceType referenceType;

    private UUID referenceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Transaction transaction)) return false;

        if (transaction.id == null || this.id == null) return false;

        return id.equals(transaction.id);
    }

    @Override
    public int hashCode() {
        return id == null ? System.identityHashCode(this) : id.hashCode();
    }

    @PrePersist
    private void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (status == null) status = TransactionStatus.NEW;
    }
}