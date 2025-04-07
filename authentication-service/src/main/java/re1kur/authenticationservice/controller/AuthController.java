package re1kur.authenticationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import re1kur.authenticationservice.service.UserService;

@RestController
@RequiredArgsConstructor
public class AuthController {
    public final UserService service;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody Object user) {
//        service.register(user);
        return ResponseEntity.ok("register endpoint");
    }

    @GetMapping("test")
    public ResponseEntity<String> test(@RequestParam(required = false) String test) {
        return ResponseEntity.ok("test: %s".formatted(test == null ? "" : test));
    }

}
