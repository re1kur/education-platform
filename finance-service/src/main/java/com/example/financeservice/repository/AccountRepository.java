package com.example.financeservice.repository;

import com.example.financeservice.entity.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends CrudRepository<Account, UUID> {
    Optional<Account> findByUserId(UUID userId);

    @Modifying
    @Query(value = """
            UPDATE Account a
            SET a.blocked = true
            WHERE a.id = :accountId AND a.blocked = false
            """)
    int tryBlockAccount(@Param("accountId") UUID accountId);
}
