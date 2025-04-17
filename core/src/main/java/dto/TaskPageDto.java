package dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

public record TaskPageDto(List<TaskDto> content, TaskPageDto.Metadata metadata) {

    public static TaskPageDto of(Page<TaskDto> page) {
        Metadata metadata = new Metadata(page.getNumber(), page.getSize(), page.getTotalPages());
        return new TaskPageDto(page.getContent(), metadata);
    }

    @Value
    @AllArgsConstructor
    private static class Metadata {
        Integer page;
        Integer size;
        Integer totalElements;
    }
}
