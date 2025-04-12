package re1kur.mailservice.mapper.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import re1kur.mailservice.dto.VerificationCodeGenerationEvent;
import re1kur.mailservice.mapper.EventMapper;

@Component
public class DefaultEventMapper implements EventMapper {
    @Value("${spring.mail.username}")
    private String from;

    @Override
    public SimpleMailMessage message(VerificationCodeGenerationEvent event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setSubject("Verification Code");
        message.setText(event.code());
        message.setTo(event.email());
        return message;
    }

    @Override
    public SimpleMailMessage welcomeMessage(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setSubject("Welcome mail.");
        message.setText("Hello there, %s.".formatted(email));
        message.setTo(email);
        return message;
    }
}
