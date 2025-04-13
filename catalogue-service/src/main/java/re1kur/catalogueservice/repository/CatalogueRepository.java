package re1kur.catalogueservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import re1kur.catalogueservice.entity.CatalogueGoods;

import java.util.List;

public interface CatalogueRepository extends CrudRepository<CatalogueGoods, Integer> {
    @Query(value = """
            FROM CatalogueGoods as c ORDER BY c.order 
            """)
    List<CatalogueGoods> findAllAndOrderByOrder();
}
