package re1kur.verificationservice.generator.impl;

import re1kur.verificationservice.entity.Code;
import re1kur.verificationservice.generator.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DefaultCodeGenerator implements CodeGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();

    @Value("${custom.verification-code.ttl}")
    private Integer ttlCode;

    @Override
    public Code generate(String email) {
        String content = getSecureRandomCode();
        return Code.builder()
                .content(content)
                .email(email)
                .expiresAt(LocalDateTime.now().plusMinutes(ttlCode))
                .build();
    }


    private String getSecureRandomCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }
}
