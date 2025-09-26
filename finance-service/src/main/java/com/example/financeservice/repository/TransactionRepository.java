package com.example.financeservice.repository;

import com.example.financeservice.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, UUID> {
    @Query(value = """
            FROM Transaction t WHERE
            t.account.userId = :userId
            """)
    Page<Transaction> findAllByUserId(Pageable pageable,
                                      @Param("userId") UUID userId);
}
