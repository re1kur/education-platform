package com.example.balanceservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import re1kur.balanceservice.service.BalanceService;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final BalanceService service;

//    @GetMapping("get")
//    public ResponseEntity<BalanceDto> getBalance(@AuthenticationPrincipal Jwt jwt) throws UserNotFoundException, NoSubjectClaimException {
//        BalanceDto dto = service.getBalance(jwt.getSubject());
//        return ResponseEntity.status(HttpStatus.FOUND).body(dto);
//    }
}
