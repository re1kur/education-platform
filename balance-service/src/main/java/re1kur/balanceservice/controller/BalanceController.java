package re1kur.balanceservice.controller;

import dto.BalanceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import exception.NoSubjectClaimException;
import exception.UserNotFoundException;
import re1kur.balanceservice.service.BalanceService;

@RestController
@RequestMapping("api/balance")
@RequiredArgsConstructor
public class BalanceController {
    private final BalanceService service;

    @GetMapping("get")
    public ResponseEntity<BalanceDto> getBalance(@AuthenticationPrincipal Jwt jwt) throws UserNotFoundException, NoSubjectClaimException {
        BalanceDto dto = service.getBalance(jwt.getSubject());
        return ResponseEntity.status(HttpStatus.FOUND).body(dto);
    }
}
