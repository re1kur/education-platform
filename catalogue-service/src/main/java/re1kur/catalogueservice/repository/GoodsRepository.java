package re1kur.catalogueservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import re1kur.catalogueservice.entity.Goods;

import java.math.BigDecimal;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Integer> {
    Boolean existsByTitle(String title);

    Page<Goods> findAll(Pageable pageable, Integer categoryId, BigDecimal price, String title);
}
