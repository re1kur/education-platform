package re1kur.taskservice.mapper.impl;

import org.springframework.stereotype.Component;
import re1kur.taskservice.dto.TaskDto;
import re1kur.taskservice.dto.TaskWriteDto;
import re1kur.taskservice.entity.Task;
import re1kur.taskservice.mapper.TaskMapper;
import re1kur.taskservice.mapper.func.ReadTaskMapFunction;
import re1kur.taskservice.mapper.func.WriteTaskMapFunction;

@Component
public class DefaultTaskMapper implements TaskMapper {
    private static final ReadTaskMapFunction mapReadTask = new ReadTaskMapFunction();
    private static final WriteTaskMapFunction mapWriteTask = new WriteTaskMapFunction();

    @Override
    public TaskDto read(Task task) {
        return mapReadTask.apply(task);
    }

    @Override
    public Task write(TaskWriteDto dto) {
        return mapWriteTask.apply(dto);
    }
}
