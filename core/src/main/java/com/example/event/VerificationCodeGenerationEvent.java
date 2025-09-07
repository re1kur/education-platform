package com.example.event;

public record VerificationCodeGenerationEvent(String email, String code) {
}
