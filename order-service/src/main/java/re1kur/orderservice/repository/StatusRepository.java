package re1kur.orderservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import re1kur.orderservice.entity.Status;

@Repository
public interface StatusRepository extends CrudRepository<Status, Integer> {
}
