package dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

public record GoodsPageDto(List<GoodsDto> content, Metadata metadata) {

    public static GoodsPageDto of(Page<GoodsDto> page) {
        Metadata meta = new Metadata(page.getNumber(), page.getSize(), page.getTotalPages());
        return new GoodsPageDto(page.getContent(), meta);
    }

    @Value
    @AllArgsConstructor
    private static class Metadata {
        Integer page;
        Integer size;
        Integer totalElements;
    }
}
