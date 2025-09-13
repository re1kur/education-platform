package com.example.catalogueservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.catalogueservice.entity.Category;

import java.util.UUID;

@Repository
public interface CategoryRepository extends CrudRepository<Category, UUID> {
    boolean existsByTitle(String title);

    @Query(value = """
            FROM Category c WHERE
            (:title IS NULL OR lower(c.title) LIKE lower(concat('%', cast(:title as string), '%')))
            """)
    Page<Category> findAll(
            Pageable pageable,
            @Param("title") String title);
}
