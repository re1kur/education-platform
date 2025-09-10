package com.example.catalogueservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.catalogueservice.entity.CatalogueProduct;

import java.util.UUID;


public interface CatalogueRepository extends CrudRepository<CatalogueProduct, UUID> {
    @Query(value = """
            FROM CatalogueProduct cp
            ORDER BY cp.priority
            """)
    Page<CatalogueProduct> findAll(Pageable pageable);
}
