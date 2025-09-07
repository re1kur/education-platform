package com.example.taskservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "tasks")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String description;

    private String previewDescription;

    private Integer cost;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "task_files", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "file_id")
    private List<UUID> fileIds = new ArrayList<>();

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<TaskAttempt> taskAttempts = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (!(o instanceof Task task)) return false;

        if (id == null || task.id == null) {
            return false;
        }

        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return id == null ? System.identityHashCode(this) : id.hashCode();
    }
}
