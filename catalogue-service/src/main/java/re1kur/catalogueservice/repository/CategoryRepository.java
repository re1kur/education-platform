package re1kur.catalogueservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import re1kur.catalogueservice.entity.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
    boolean existsByTitle(String title);
}
