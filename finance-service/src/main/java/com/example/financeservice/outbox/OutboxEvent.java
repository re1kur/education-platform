package com.example.financeservice.outbox;

import com.example.enums.OutboxType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "outbox_events")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OutboxEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private String payload;

    @Enumerated(EnumType.STRING)
    private OutboxType type;

    private LocalDateTime reservedTo;

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (!(o instanceof OutboxEvent event)) return false;

        if (id == null || event.id == null) {
            return false;
        }

        return id.equals(event.id);
    }

    @Override
    public int hashCode() {
        return id == null ? System.identityHashCode(this) : id.hashCode();
    }
}