package re1kur.mailservice.mapper;

import org.springframework.mail.SimpleMailMessage;

public interface EventMapper {
    SimpleMailMessage codeMessage(String message);

    SimpleMailMessage welcomeMessage(String email);

}
