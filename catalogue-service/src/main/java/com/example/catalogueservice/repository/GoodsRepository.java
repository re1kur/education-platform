package com.example.catalogueservice.repository;

import com.example.catalogueservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface GoodsRepository extends JpaRepository<Product, Integer> {
    Boolean existsByTitle(String title);

    @Query(value =
            """
                    from Goods g where 
                    ((lower(g.title) like lower(cocnat('%',:title,'%'))) or :title is null) and
                    ((g.price = :price) or :price is null) and
                    ((g.category.id = :categoryId) or :categoryId is null)
                    """)
    Page<Product> findAllByFilter(
            Pageable pageable,
            @Param("categoryId") Integer categoryId,
            @Param("price") BigDecimal price,
            @Param("title") String title);
}
