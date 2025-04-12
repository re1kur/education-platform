package re1kur.verificationservice.dto;

import java.io.Serializable;

public record ResultCheckVerification(boolean isExists, Boolean isVerified) {
}
