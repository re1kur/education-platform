package re1kur.taskservice.mapper;

import dto.TaskDto;
import payload.TaskPayload;
import re1kur.taskservice.entity.Task;

public interface TaskMapper {
    TaskDto read(Task task);

    Task write(TaskPayload dto);
}
