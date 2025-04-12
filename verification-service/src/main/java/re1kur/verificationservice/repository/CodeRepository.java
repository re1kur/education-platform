package re1kur.verificationservice.repository;

import re1kur.verificationservice.entity.Code;
import org.springframework.data.repository.CrudRepository;

public interface CodeRepository extends CrudRepository<Code, String> {
}
