package re1kur.mailservice.dto;

public record VerificationCodeGenerationEvent(String email, String code) {
}
