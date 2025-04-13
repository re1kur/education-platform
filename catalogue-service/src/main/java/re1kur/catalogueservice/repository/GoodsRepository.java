package re1kur.catalogueservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import re1kur.catalogueservice.entity.Goods;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Integer> {
    Boolean existsByTitle(String title);
}
