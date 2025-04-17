package re1kur.catalogueservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import re1kur.catalogueservice.entity.CatalogueGoods;


public interface CatalogueRepository extends CrudRepository<CatalogueGoods, Integer> {
    @Query(value = """
            FROM CatalogueGoods as c ORDER BY c.order 
            """)
    Page<CatalogueGoods> findAllAndOrderByOrder(Pageable pageable);
}
