package com.example.taskservice.entity;

import com.example.other.TaskAttemptStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "task_attempts")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JoinColumn(name = "task_id")
    @ManyToOne
    private Task task;

    private UUID userId;

    private String comment;

    @Enumerated(EnumType.STRING)
    private TaskAttemptStatus status;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "task_attempt_files", joinColumns = @JoinColumn(name = "task_attempt_id"))
    @Column(name = "file_id")
    private List<UUID> fileIds = new ArrayList<>();

    @OneToOne(mappedBy = "taskAttempt", cascade = CascadeType.ALL, orphanRemoval = true)
    private TaskAttemptResult result;

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (!(o instanceof TaskAttempt taskAttempt)) return false;

        if (id == null || taskAttempt.id == null) {
            return false;
        }

        return id.equals(taskAttempt.id);
    }

    @Override
    public int hashCode() {
        return id == null ? System.identityHashCode(this) : id.hashCode();
    }
}
