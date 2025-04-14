package payload;

import jakarta.validation.constraints.NotBlank;

public record CategoryPayload(
        @NotBlank String title,
        String description) {
}
