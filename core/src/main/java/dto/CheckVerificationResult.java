package dto;

public record CheckVerificationResult(
        boolean isExists,
        Boolean isVerified) {
}
