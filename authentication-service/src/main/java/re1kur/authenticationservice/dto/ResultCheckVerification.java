package re1kur.authenticationservice.dto;

import java.io.Serializable;

public record ResultCheckVerification (boolean isExists, Boolean isVerified) implements Serializable {
}
