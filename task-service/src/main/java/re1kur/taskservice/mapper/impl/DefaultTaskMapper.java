package re1kur.taskservice.mapper.impl;

import exception.TrackNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import dto.TaskDto;
import payload.TaskPayload;
import payload.TaskUpdatePayload;
import re1kur.taskservice.entity.Task;
import re1kur.taskservice.entity.Track;
import re1kur.taskservice.mapper.TaskMapper;
import re1kur.taskservice.repository.TrackRepository;

@Component
@RequiredArgsConstructor
public class DefaultTaskMapper implements TaskMapper {
    private final TrackRepository trackRepo;

    @Override
    public TaskDto read(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .trackId(task.getTrack().getId())
                .description(task.getDescription())
                .level(task.getLevel())
                .cost(task.getCost().intValue())
                .build();
    }

    @Override
    public Task write(TaskPayload payload) throws TrackNotFoundException {
        Track track = trackRepo.findById(payload.trackId())
                .orElseThrow(() -> new TrackNotFoundException("Track with id '%s' does not exist.".formatted(payload.trackId())));
        return Task.builder()
                .name(payload.name())
                .track(track)
                .description(payload.description())
                .level(payload.level())
                .cost(payload.cost())
                .build();
    }

    @Override
    public Task update(Task task, TaskUpdatePayload payload) throws TrackNotFoundException {
        Track track = trackRepo.findById(payload.trackId())
                .orElseThrow(() -> new TrackNotFoundException("Track with id '%s' does not exist.".formatted(payload.trackId())));
        task.setTrack(track);
        task.setName(payload.name());
        task.setDescription(payload.description());
        task.setLevel(payload.level());
        task.setCost(payload.cost());
        return task;
    }
}
