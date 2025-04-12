package re1kur.verificationservice.mq.event;

public record VerificationCodeGenerationEvent (String email, String code) {
}
