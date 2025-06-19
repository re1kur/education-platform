package dto;


public record TaskDto(
        Integer id,
        Integer trackId,
        String name,
        String description,
        Integer level,
        Integer cost) {
}
