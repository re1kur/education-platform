package com.example.catalogueservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "products")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private Integer price;

    private Boolean forSale;

    private Boolean single;

    private String description;

    private String previewDescription;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_files", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "file_id")
    private List<UUID> fileIds = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (!(o instanceof Product product)) return false;

        if (id == null || product.id == null) {
            return false;
        }

        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return id == null ? System.identityHashCode(this) : id.hashCode();
    }

    @PrePersist
    private void prePersist() {
        if (forSale == null) forSale = true;
        if (single == null) single = false;
    }
}