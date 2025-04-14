package re1kur.authenticationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dto.CheckVerificationResult;
import payload.UserPayload;
import exception.UserAuthenticationException;
import exception.UserRegistrationException;
import re1kur.authenticationservice.jwt.entity.Token;
import re1kur.authenticationservice.service.AuthenticationService;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody UserPayload payload) throws UserRegistrationException {
        service.register(payload);
        return ResponseEntity.ok("The user is registered successfully");
    }

    @PostMapping("login")
    public ResponseEntity<String> authenticate(@RequestBody UserPayload user) throws UserAuthenticationException {
        Token token = service.authenticate(user);
        return ResponseEntity.ok(token.toString());
    }

    @GetMapping("verification")
    public ResponseEntity<CheckVerificationResult> authenticate(@RequestParam String email) {
        return service.checkVerification(email);
    }
}
