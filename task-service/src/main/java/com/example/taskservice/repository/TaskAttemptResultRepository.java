package com.example.taskservice.repository;

import com.example.taskservice.entity.TaskAttemptResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskAttemptResultRepository extends CrudRepository<TaskAttemptResult, UUID> {
    @Query(value = """
            FROM TaskAttemptResult tar WHERE
            (:checkedBy IS NULL OR tar.checkedBy = :checkedBy) AND
            (:attemptId IS NULL OR tar.taskAttemptId = :attemptId) AND
            (:comment IS NULL OR lower(tar.comment) LIKE lower(concat('%', cast(:comment as string), '%')))
            """)
    Page<TaskAttemptResult> findAll(
            Pageable pageable,
            @Param("checkedBy") UUID checkedBy,
            @Param("attemptId") UUID attemptId,
            @Param("comment") String comment);
}
