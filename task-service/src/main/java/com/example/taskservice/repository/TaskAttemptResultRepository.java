package com.example.taskservice.repository;

import com.example.taskservice.entity.TaskAttemptResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskAttemptResultRepository extends CrudRepository<TaskAttemptResult, UUID> {
}
