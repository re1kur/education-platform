package com.example.taskservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.taskservice.entity.Task;

import java.math.BigDecimal;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    @Query(value = """
            from Task t where
            (lower(t.name) like lower(concat('%', :name, '%')) or :name is null) and
            ((t.cost = :cost) or :cost is null)
            """)
    Page<Task> findAll(Pageable pageable,
                       @Param("name") String name,
                       @Param("cost") BigDecimal cost);
}
