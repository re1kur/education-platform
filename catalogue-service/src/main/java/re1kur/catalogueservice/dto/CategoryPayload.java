package re1kur.catalogueservice.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryPayload(
        @NotBlank String title,
        String description) {
}
