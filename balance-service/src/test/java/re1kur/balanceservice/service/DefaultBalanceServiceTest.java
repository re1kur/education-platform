package re1kur.balanceservice.service;

import exception.NoSubjectClaimException;
import exception.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import re1kur.balanceservice.entity.Balance;
import re1kur.balanceservice.repository.BalanceRepository;
import re1kur.balanceservice.service.impl.DefaultBalanceService;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class DefaultBalanceServiceTest {
    @InjectMocks
    private DefaultBalanceService service;

    @Mock
    private BalanceRepository repo;

    @Test
    void getBalanceWhenUserValid() {
        String subject = "123e4567-e89b-12d3-a456-426614174000";
        Balance balance = Balance.builder()
                .balance(new BigDecimal("100.1"))
                .userId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))
                .isBlocked(false)
                .build();

        Mockito.when(repo.findById(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))).thenReturn(Optional.of(balance));

        Assertions.assertDoesNotThrow(() -> service.getBalance(subject));

        Mockito.verify(repo, Mockito.times(1)).findById(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
    }

    @Test
    void getBalanceWhenSubjectInvalid() {
        String subject = "";

        Assertions.assertThrows(NoSubjectClaimException.class, () -> service.getBalance(subject));

        Mockito.verifyNoInteractions(repo);
    }

    @Test
    void getBalanceWhenUserNotFound() {
        String subject = "123e4567-e89b-12d3-a456-426614174000";

        Mockito.when(repo.findById(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> service.getBalance(subject));

        Mockito.verify(repo, Mockito.times(1)).findById(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
    }
}