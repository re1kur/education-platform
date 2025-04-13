package re1kur.taskservice.service;

import re1kur.taskservice.dto.TaskDto;
import re1kur.taskservice.dto.TaskPayload;

import java.util.List;


public interface TaskService {
    List<TaskDto> getList();

    void create(TaskPayload payload);
}
