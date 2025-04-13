package re1kur.mailservice.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import re1kur.mailservice.event.UserRegistrationEvent;
import re1kur.mailservice.event.VerificationCodeGenerationEvent;
import re1kur.mailservice.mapper.EventMapper;

@Component
@RequiredArgsConstructor
public class DefaultEventMapper implements EventMapper {
    @Value("${spring.mail.username}")
    private String from;
    private final ObjectMapper serializer;

    @Override
    @SneakyThrows
    public SimpleMailMessage codeMessage(String message) {
        VerificationCodeGenerationEvent event = serializer.readValue(message, VerificationCodeGenerationEvent.class);
        SimpleMailMessage plainMessage = new SimpleMailMessage();
        plainMessage.setFrom(from);
        plainMessage.setSubject("Verification Code");
        plainMessage.setText(event.code());
        plainMessage.setTo(event.email());
        return plainMessage;
    }

    @Override
    @SneakyThrows
    public SimpleMailMessage welcomeMessage(String message) {
        UserRegistrationEvent event = serializer.readValue(message, UserRegistrationEvent.class);
        SimpleMailMessage plainMessage = new SimpleMailMessage();
        plainMessage.setFrom(from);
        plainMessage.setSubject("Welcome mail.");
        plainMessage.setText("Hello there, %s.".formatted(event.email()));
        plainMessage.setTo(event.email());
        return plainMessage;
    }
}
