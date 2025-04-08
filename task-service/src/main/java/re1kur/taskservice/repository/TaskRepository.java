package re1kur.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import re1kur.taskservice.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
