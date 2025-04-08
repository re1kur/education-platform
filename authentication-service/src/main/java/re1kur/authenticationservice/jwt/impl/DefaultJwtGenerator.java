package re1kur.authenticationservice.jwt.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import re1kur.authenticationservice.jwt.JwtGenerator;
import re1kur.authenticationservice.jwt.entity.Credentials;
import re1kur.authenticationservice.jwt.entity.Token;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;


@Component
public class DefaultJwtGenerator implements JwtGenerator {
    private static final ObjectMapper jsonMapper = new ObjectMapper();
    private static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

    @Override
    @SneakyThrows
    public Token generate(Credentials credentials) {
        // header
        final Map<String, String> headers = Map.of("typ", "JWT", "alg", "HS256");

        String jsonHeader = jsonMapper.writeValueAsString(headers);
        String header = encoder.encodeToString(jsonHeader.getBytes(StandardCharsets.UTF_8));

        // payload
        final Map<String, String> body = credentials.getClaims();

        String payloadJson = jsonMapper.writeValueAsString(body);
        String payload = encoder.encodeToString(payloadJson.getBytes(StandardCharsets.UTF_8));

        //signature
        String signatureJson = jsonMapper.writeValueAsString(null);
        String signature = encoder.encodeToString(signatureJson.getBytes(StandardCharsets.UTF_8));

        return new Token(header, payload, signature);
    }
}
