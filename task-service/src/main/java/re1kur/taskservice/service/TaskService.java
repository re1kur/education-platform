package re1kur.taskservice.service;

import dto.TaskDto;
import payload.TaskPayload;

import java.util.List;


public interface TaskService {
    List<TaskDto> getList();

    void create(TaskPayload payload);
}
