package com.example.catalogueservice.repository;

import com.example.catalogueservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends CrudRepository<Product, UUID> {
    Boolean existsByTitle(String title);

    @Query(value =
            """
                    from Product p WHERE
                    (:title IS NULL OR (lower(p.title) LIKE lower(concat('%', cast(:comment as string), '%'))) AND
                    (:price IS NULL (p.price = :price)) AND
                    (:categoryId IS NULL OR (p.category.id = :categoryId))
                    """)
    Page<Product> findAll(
            Pageable pageable,
            @Param("categoryId") UUID categoryId,
            @Param("price") Integer price,
            @Param("title") String title);
}
