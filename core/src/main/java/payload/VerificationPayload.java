package payload;

public record VerificationPayload(
        String email,
        String code
) {
}
