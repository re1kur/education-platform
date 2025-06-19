package re1kur.taskservice.service;

import dto.TaskPageDto;
import dto.TaskDto;
import exception.TaskNotFoundException;
import exception.TrackNotFoundException;
import filter.TaskFilter;
import org.springframework.data.domain.Pageable;
import payload.TaskPayload;
import payload.TaskUpdatePayload;

import java.util.List;


public interface TaskService {
    List<TaskDto> getList();

    void create(TaskPayload payload) throws TrackNotFoundException;

    void update(TaskUpdatePayload payload) throws TrackNotFoundException, TaskNotFoundException;

    void delete(Integer id) throws TaskNotFoundException;

    TaskDto getById(Integer id) throws TaskNotFoundException;

    TaskPageDto getPage(Pageable pageable, TaskFilter filter);

    void attachFile(String userId,Integer taskId, String fileId) throws TaskNotFoundException;
}
