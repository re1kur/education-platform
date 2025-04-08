package re1kur.authenticationservice.jwt;

import re1kur.authenticationservice.entity.User;
import re1kur.authenticationservice.jwt.entity.Token;

@FunctionalInterface
public interface JwtProvider {
    Token provide(User user);
}
