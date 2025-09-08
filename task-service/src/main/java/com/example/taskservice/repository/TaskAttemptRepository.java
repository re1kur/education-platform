package com.example.taskservice.repository;

import com.example.other.TaskAttemptStatus;
import com.example.taskservice.entity.TaskAttempt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskAttemptRepository extends CrudRepository<TaskAttempt, UUID> {
    Page<TaskAttempt> findAllByTaskIdAndUserId(Pageable pageable, UUID taskId, UUID userId);

    Optional<TaskAttempt> findByIdAndUserId(UUID attemptId, UUID userId);

    @Query(value = """
            FROM TaskAttempt ta WHERE
            (:userId IS NULL OR ta.userId = :userId) AND
            (:taskId IS NULL OR ta.task.id = :taskId) AND
            (:status IS NULL OR ta.status = :status)
            """)
    Page<TaskAttempt> findAll(Pageable pageable,
                              @Param("userId") UUID userId,
                              @Param("taskId") UUID taskId,
                              @Param("status") TaskAttemptStatus status);
}
