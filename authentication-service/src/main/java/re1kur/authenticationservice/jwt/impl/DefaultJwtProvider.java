package re1kur.authenticationservice.jwt.impl;

import lombok.RequiredArgsConstructor;
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
    public Token provide(User user) {
        Credentials credentials = new Credentials(user);
        Token jwt = jwtGenerator.generate(credentials);
        return jwt;
    }
}
