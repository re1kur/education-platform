package re1kur.transactionservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import re1kur.transactionservice.entity.TransactionType;

@Repository
public interface TransactionTypeRepository extends CrudRepository<TransactionType, Integer> {
    TransactionType findByName(String name);
}
