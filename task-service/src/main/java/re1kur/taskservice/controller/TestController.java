package re1kur.taskservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test-api")
public class TestController {
    @Value("${spring.application.name}")
    private String serviceName;

    @GetMapping
    public ResponseEntity<String> greeting() {
        return ResponseEntity.ok().body("Greetings by %s".formatted(serviceName));
    }

    @GetMapping("token")
    public ResponseEntity<String> testToken() {
        return ResponseEntity.ok().body("If you see this message, it means that the token is valid.");
    }
}
