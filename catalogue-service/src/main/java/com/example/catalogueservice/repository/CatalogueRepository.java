package com.example.catalogueservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.catalogueservice.entity.CatalogueProduct;


public interface CatalogueRepository extends CrudRepository<CatalogueProduct, Integer> {
    @Query(value = """
            FROM CatalogueGoods as c ORDER BY c.order 
            """)
    Page<CatalogueProduct> findAllAndOrderByOrder(Pageable pageable);
}
