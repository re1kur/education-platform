package re1kur.coinsservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import re1kur.coinsservice.entity.TransactionType;

@Repository
public interface TransactionTypeRepository extends CrudRepository<TransactionType, Integer> {
}
