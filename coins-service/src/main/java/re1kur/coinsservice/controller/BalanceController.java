package re1kur.coinsservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import re1kur.coinsservice.exception.NoSubjectClaimException;
import re1kur.coinsservice.exception.UserNotFoundException;
import re1kur.coinsservice.service.BalanceService;

@RestController
@RequestMapping("api/balance")
@RequiredArgsConstructor
public class BalanceController {
    private final BalanceService service;

    @GetMapping("get")
    public ResponseEntity<String> getBalance(@AuthenticationPrincipal Jwt jwt) throws UserNotFoundException, NoSubjectClaimException {
        return service.getBalance(jwt.getSubject());
    }
}
