package re1kur.coinsservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import re1kur.coinsservice.dto.TransactionDto;
import re1kur.coinsservice.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService service;

    @GetMapping("history")
    public ResponseEntity<List<TransactionDto>> getTransactionsHistory(@AuthenticationPrincipal Jwt jwt) {
        return service.getHistory(jwt.getSubject());
    }
}
