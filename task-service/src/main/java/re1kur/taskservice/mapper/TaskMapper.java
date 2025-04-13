package re1kur.taskservice.mapper;

import re1kur.taskservice.dto.TaskDto;
import re1kur.taskservice.dto.TaskPayload;
import re1kur.taskservice.entity.Task;

public interface TaskMapper {
    TaskDto read(Task task);

    Task write(TaskPayload dto);
}
