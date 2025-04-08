package re1kur.authenticationservice.jwt.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import re1kur.authenticationservice.entity.User;
import re1kur.authenticationservice.jwt.entity.Credentials;
import re1kur.authenticationservice.jwt.JwtGenerator;
import re1kur.authenticationservice.jwt.JwtProvider;
import re1kur.authenticationservice.jwt.entity.Token;

@Component
@RequiredArgsConstructor
public class DefaultJwtProvider implements JwtProvider {
    private final JwtGenerator jwtGenerator;

    @Override
    @SneakyThrows
    public Token provide(User user) {
        Credentials credentials = new Credentials(user);
        return jwtGenerator.generate(credentials);
    }
}
