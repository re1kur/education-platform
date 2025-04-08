package re1kur.taskservice.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TaskDto {
    Integer id;
    Integer trackId;
    String name;
    String description;
    Integer level;
    Integer cost;
}
