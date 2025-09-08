package com.example.fileservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "files")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class File {
    @Id
    private UUID id;

    private String extension;

    private String url;

    @Column(insertable = false, updatable = false)
    private LocalDateTime uploadedAt;

    private LocalDateTime urlExpiresAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof File file)) return false;

        if (id == null || file.id == null) return false;

        return id.equals(file.id);
    }

    @Override
    public int hashCode() {
        return id == null ? System.identityHashCode(this) : id.hashCode();
    }
}
