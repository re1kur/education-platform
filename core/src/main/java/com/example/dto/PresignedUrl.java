package com.example.dto;

import java.time.Instant;

public record PresignedUrl(String url, Instant expiration) {
}
