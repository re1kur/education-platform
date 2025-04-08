package re1kur.authenticationservice.jwt.entity;

import lombok.Value;
import re1kur.authenticationservice.entity.Role;
import re1kur.authenticationservice.entity.User;

import java.util.HashMap;
import java.util.Map;

@Value
public class Credentials {
    Map<String, String> claims;

    public Credentials(User user) {
        claims = new HashMap<>();
        claims.put("sub", user.getId().toString());
        claims.put("email", user.getEmail());
        claims.put("verified", user.getIsEmailVerified().toString());
        claims.put("scope", user.getRoles().stream().map(Role::getName).toList().toString());
    }
}
