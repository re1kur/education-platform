package re1kur.verificationservice.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payload.VerificationPayload;
import re1kur.verificationservice.service.CodeService;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class VerificationController {
    private final CodeService service;

    @PostMapping("send-code")
    public ResponseEntity<String> sendCode(@RequestParam String email) throws UserNotFoundException, UserAlreadyVerifiedException, JsonProcessingException {
        return service.generateCode(email);
    }

    @PostMapping("verify")
    public ResponseEntity<String> verify(@RequestBody VerificationPayload payload) throws UserNotFoundException, UserVerificationException,
            ExpiredCodeVerificationException, IncorrectCodeVerification,
            NotFoundCodeVerificationException, UserAlreadyVerifiedException {
        return service.verify(payload);
    }
}
