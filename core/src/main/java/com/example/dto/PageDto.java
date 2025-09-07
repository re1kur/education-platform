package com.example.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PageDto<T> (
        List<T> content,
        int page,
        int size,
        int total,
        boolean hasNext,
        boolean hasPrev
) {
}
