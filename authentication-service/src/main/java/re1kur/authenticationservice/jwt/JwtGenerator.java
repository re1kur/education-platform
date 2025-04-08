package re1kur.authenticationservice.jwt;

import re1kur.authenticationservice.jwt.entity.Credentials;
import re1kur.authenticationservice.jwt.entity.Token;

@FunctionalInterface
public interface JwtGenerator {
    Token generate(Credentials credentials);
}
