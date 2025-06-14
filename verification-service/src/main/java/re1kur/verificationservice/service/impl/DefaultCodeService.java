package re1kur.verificationservice.service.impl;

import exception.*;
import org.springframework.transaction.annotation.Transactional;
import re1kur.verificationservice.client.AuthenticationClient;
import payload.VerificationPayload;
import re1kur.verificationservice.entity.Code;
import re1kur.verificationservice.generator.CodeGenerator;
import lombok.RequiredArgsConstructor;
import re1kur.verificationservice.mq.publisher.EventPublisher;
import org.springframework.stereotype.Component;
import re1kur.verificationservice.repository.CodeRepository;
import re1kur.verificationservice.service.CodeService;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DefaultCodeService implements CodeService {
    private final CodeRepository repo;
    private final CodeGenerator generator;
    private final EventPublisher publisher;
    private final AuthenticationClient client;

    @Override
    @Transactional
    public void generateCode(String email) throws UserNotFoundException, UserAlreadyVerifiedException {
        client.checkVerification(email);
        Code code = generator.generate(email);
        repo.save(code);
        publisher.publishGenerationCodeEvent(code);
    }

    @Override
    public void verify(VerificationPayload payload) throws IncorrectCodeVerification, UserNotFoundException, UserAlreadyVerifiedException, NotFoundCodeVerificationException, ExpiredCodeVerificationException {
        String email = payload.email();
        client.checkVerification(email);
        Code code = repo.findById(email).orElseThrow(() -> new NotFoundCodeVerificationException("Code not found"));
        if (handleVerificationCode(code, payload.code()))
            publisher.publishUserVerificationEvent(email);
    }

    private boolean handleVerificationCode(Code code, String toCompare) throws ExpiredCodeVerificationException, IncorrectCodeVerification {
        if (LocalDateTime.now().isAfter(code.getExpiresAt()))
            throw new ExpiredCodeVerificationException("Code is expired.");
        if (!code.getContent().equals(toCompare)) throw new IncorrectCodeVerification("Provided code is incorrect.");
        return true;
    }
}
