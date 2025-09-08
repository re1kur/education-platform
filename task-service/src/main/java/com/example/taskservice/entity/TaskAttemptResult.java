package com.example.taskservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "task_attempt_results")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskAttemptResult {
    @Id
    private UUID taskAttemptId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "task_attempt_id")
    private TaskAttempt taskAttempt;

    private UUID checkedBy;

    private LocalDateTime checkedAt;

    private String comment;

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (!(o instanceof TaskAttemptResult result)) return false;

        if (taskAttemptId == null || result.taskAttemptId == null) {
            return false;
        }

        return taskAttemptId.equals(result.taskAttemptId);
    }

    @Override
    public int hashCode() {
        return taskAttemptId == null ? System.identityHashCode(this) : taskAttemptId.hashCode();
    }

    @PrePersist
    public void prePersist() {
        if (checkedAt == null) checkedAt = LocalDateTime.now();
    }
}
