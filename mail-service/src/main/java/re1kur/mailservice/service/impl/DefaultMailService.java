package re1kur.mailservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import re1kur.mailservice.mapper.EventMapper;
import re1kur.mailservice.service.MailService;

@Component
@RequiredArgsConstructor
public class DefaultMailService implements MailService {
    private final MailSender sender;
    private final EventMapper mapper;

    @Override
    public void sendGenerationCode(String message) {
        SimpleMailMessage msg = mapper.codeMessage(message);
        sender.send(msg);
    }

    @Override
    public void sendWelcomeMail(String message) {
        SimpleMailMessage msg = mapper.welcomeMessage(message);
        sender.send(msg);
    }
}
