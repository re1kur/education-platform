package re1kur.mailservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import re1kur.mailservice.dto.VerificationCodeGenerationEvent;
import re1kur.mailservice.mapper.EventMapper;
import re1kur.mailservice.service.MailService;

@Component
@RequiredArgsConstructor
public class DefaultMailService implements MailService {
    private final MailSender sender;
    private final ObjectMapper serializer;
    private final EventMapper mapper;

    @Override
    @SneakyThrows
    public void sendGenerationCode(String message) {
        VerificationCodeGenerationEvent event = serializer.readValue(message, VerificationCodeGenerationEvent.class);
        SimpleMailMessage msg = mapper.message(event);
        sender.send(msg);
    }

    @Override
    public void sendWelcomeMail(String email) {
        SimpleMailMessage msg = mapper.welcomeMessage(email);
        sender.send(msg);
    }
}
