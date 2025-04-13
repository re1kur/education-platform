package re1kur.catalogueservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryUpdatePayload(
        @NotNull Integer id,
        @NotBlank String title,
        String description) {
}
