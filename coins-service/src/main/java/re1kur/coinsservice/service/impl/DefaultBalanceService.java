package re1kur.coinsservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import re1kur.coinsservice.dto.BalanceDto;
import re1kur.coinsservice.entity.Balance;
import re1kur.coinsservice.exception.NoSubjectClaimException;
import re1kur.coinsservice.exception.UserNotFoundException;
import re1kur.coinsservice.repository.BalanceRepository;
import re1kur.coinsservice.service.BalanceService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultBalanceService implements BalanceService {
    private final BalanceRepository repo;

    @Override
    public ResponseEntity<BalanceDto> getBalance(String userId) throws NoSubjectClaimException, UserNotFoundException {
        if (userId == null || userId.isEmpty()) throw new NoSubjectClaimException("No user ID found");
        Balance balance = repo.findById(UUID.fromString(userId)).orElseThrow(() -> new UserNotFoundException("User not found"));
        BalanceDto dto = new BalanceDto(balance.getUserId().toString(), balance.getBalance());
        return ResponseEntity.status(HttpStatus.FOUND).body(dto);
    }
}
