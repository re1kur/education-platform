package com.example.catalogueservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "categories")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String previewDescription;

    private String description;

    private UUID titleImageId;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> products;

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (!(o instanceof Category category)) return false;

        if (id == null || category.id == null) {
            return false;
        }

        return id.equals(category.id);
    }

    @Override
    public int hashCode() {
        return id == null ? System.identityHashCode(this) : id.hashCode();
    }
}
