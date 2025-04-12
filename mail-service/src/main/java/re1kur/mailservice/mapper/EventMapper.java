package re1kur.mailservice.mapper;

import org.springframework.mail.SimpleMailMessage;
import re1kur.mailservice.dto.VerificationCodeGenerationEvent;

public interface EventMapper {
    SimpleMailMessage message(VerificationCodeGenerationEvent event);

    SimpleMailMessage welcomeMessage(String email);
}
