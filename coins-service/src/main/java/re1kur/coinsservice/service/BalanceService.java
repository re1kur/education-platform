package re1kur.coinsservice.service;

import org.springframework.http.ResponseEntity;
import re1kur.coinsservice.exception.NoSubjectClaimException;
import re1kur.coinsservice.exception.UserNotFoundException;

public interface BalanceService {
    ResponseEntity<String> getBalance(String userId) throws UserNotFoundException, NoSubjectClaimException;
}
