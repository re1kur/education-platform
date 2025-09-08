package com.example.other;


import java.util.UUID;


public record AttemptResultFilter(
        UUID attemptId,
        UUID checkedBy,
        String comment
) {
}
