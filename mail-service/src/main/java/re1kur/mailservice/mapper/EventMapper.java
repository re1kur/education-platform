package re1kur.mailservice.mapper;

import org.springframework.mail.SimpleMailMessage;
import re1kur.mailservice.event.UserRegistrationEvent;
import re1kur.mailservice.event.VerificationCodeGenerationEvent;

public interface EventMapper {
    SimpleMailMessage codeMessage(String message);

    SimpleMailMessage welcomeMessage(String email);

}
