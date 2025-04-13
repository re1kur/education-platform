package re1kur.catalogueservice.controller;

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
    public ResponseEntity<String> greetings() {
        return ResponseEntity.ok("Greetings from %s.".formatted(serviceName));
    }

    @GetMapping("token")
    public ResponseEntity<String> checkToken() {
        return ResponseEntity.ok("If you see this, it meant token is valid.");
    }
}
