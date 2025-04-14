package re1kur.taskservice.mapper.impl;

import org.springframework.stereotype.Component;
import dto.TaskDto;
import payload.TaskPayload;
import re1kur.taskservice.entity.Task;
import re1kur.taskservice.entity.Track;
import re1kur.taskservice.mapper.TaskMapper;

@Component
public class DefaultTaskMapper implements TaskMapper {

    @Override
    public TaskDto read(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .trackId(task.getTrack().getId())
                .description(task.getDescription())
                .level(task.getLevel())
                .cost(task.getCost())
                .build();
    }

    @Override
    public Task write(TaskPayload dto) {
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
