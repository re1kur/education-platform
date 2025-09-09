package com.example.filter;


import java.util.UUID;


public record AttemptResultsFilter(
        UUID attemptId,
        UUID checkedBy,
        String comment
) {
}
