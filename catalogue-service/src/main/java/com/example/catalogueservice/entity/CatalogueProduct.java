package com.example.catalogueservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "catalogue_products")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CatalogueProduct {
    @Id
    private Integer productId;

    @MapsId
    @JoinColumn(name = "product_id")
    @OneToOne
    private Product product;

    private Integer priority;

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (!(o instanceof CatalogueProduct catalogueProduct)) return false;

        if (productId == null || catalogueProduct.productId == null) {
            return false;
        }

        return productId.equals(catalogueProduct.productId);
    }

    @Override
    public int hashCode() {
        return productId == null ? System.identityHashCode(this) : productId.hashCode();
    }

    @PrePersist
    private void prePersist() {
        if (priority == null) priority = 0;
    }
}
