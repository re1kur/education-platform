package re1kur.authenticationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import re1kur.authenticationservice.dto.UserAuthentication;
import re1kur.authenticationservice.dto.UserVerification;
import re1kur.authenticationservice.dto.UserWriteDto;
import re1kur.authenticationservice.exception.UserAuthenticationException;
import re1kur.authenticationservice.exception.UserRegistrationException;
import re1kur.authenticationservice.exception.UserVerificationException;
import re1kur.authenticationservice.jwt.entity.Token;
import re1kur.authenticationservice.service.UserService;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody UserWriteDto user) throws UserRegistrationException {
        service.register(user);
        return ResponseEntity.ok("The user is registered successfully");
    }

    @PostMapping("login")
    public ResponseEntity<String> authenticate(@RequestBody UserAuthentication user) throws UserAuthenticationException {
        Token token = service.authenticate(user);
        return ResponseEntity.ok(token.toString());
    }

    @PostMapping("verify")
    public ResponseEntity<String> verify(@RequestBody UserVerification user) throws UserVerificationException {
        service.verify(user);
        return ResponseEntity.ok("The user is verified successfully");
    }


}
