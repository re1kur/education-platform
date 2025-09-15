package com.example.catalogueservice.outbox;

import com.example.enums.OutboxType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OutboxEventRepository extends CrudRepository<OutboxEvent, UUID> {
    @Query(value = """
            FROM OutboxEvent o WHERE
            (o.type = :type) AND
            (o.reservedTo IS NULL OR o.reservedTo < CURRENT_TIMESTAMP)
            """)
    List<OutboxEvent> findAll(
            @Param("type") OutboxType type);
}
