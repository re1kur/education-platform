package re1kur.mailservice.event;

public record VerificationCodeGenerationEvent(String email, String code) {
}
