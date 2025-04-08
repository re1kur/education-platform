package re1kur.authenticationservice.jwt;

import com.nimbusds.jose.JOSEException;
import re1kur.authenticationservice.jwt.entity.Credentials;
import re1kur.authenticationservice.jwt.entity.Token;

import java.io.IOException;
import java.text.ParseException;

@FunctionalInterface
public interface JwtGenerator {
    Token generate(Credentials credentials) throws ParseException, IOException, JOSEException;
}
