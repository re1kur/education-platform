package re1kur.taskservice.service;

import re1kur.taskservice.dto.TaskWriteDto;


public interface TaskService {
    String getList();

    void create(TaskWriteDto task);
}
