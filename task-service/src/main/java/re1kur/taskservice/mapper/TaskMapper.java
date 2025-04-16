package re1kur.taskservice.mapper;

import dto.TaskDto;
import exception.TrackNotFoundException;
import payload.TaskPayload;
import payload.TaskUpdatePayload;
import re1kur.taskservice.entity.Task;

public interface TaskMapper {
    TaskDto read(Task task);

    Task write(TaskPayload payload) throws TrackNotFoundException;

    Task update(Task task, TaskUpdatePayload payload) throws TrackNotFoundException;
}
