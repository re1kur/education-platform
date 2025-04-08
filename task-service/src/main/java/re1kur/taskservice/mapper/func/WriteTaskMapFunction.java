package re1kur.taskservice.mapper.func;

import re1kur.taskservice.dto.TaskWriteDto;
import re1kur.taskservice.entity.Task;
import re1kur.taskservice.entity.Track;

import java.util.function.Function;

public class WriteTaskMapFunction implements Function<TaskWriteDto, Task> {
    /**
     * Applies this function to the given argument.
     *
     * @param dto the function argument
     * @return the function result
     */
    @Override
    public Task apply(TaskWriteDto dto) {
        return Task.builder()
                .name(dto.getName())
                .track(Track.builder()
                        .id(dto.getTrackId())
                        .build())
                .description(dto.getDescription())
                .level(dto.getLevel())
                .cost(dto.getCost())
                .build();
    }
}
