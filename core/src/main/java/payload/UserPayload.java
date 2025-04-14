package payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserPayload(
        @Email @Size(max = 256) String email,
        @Size(max = 256, min = 6) String password) {
}
