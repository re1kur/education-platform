package re1kur.taskservice.mapper.func;

import re1kur.taskservice.dto.TaskDto;
import re1kur.taskservice.entity.Task;

import java.util.function.Function;

public class ReadTaskMapFunction implements Function<Task, TaskDto> {
    /**
     * Applies this function to the given argument.
     *
     * @param task the function argument
     * @return the function result
     */
    @Override
    public TaskDto apply(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .trackId(task.getTrack().getId())
                .description(task.getDescription())
                .level(task.getLevel())
                .cost(task.getCost())
                .build();
    }
}
