package re1kur.balanceservice.service;

import dto.BalanceDto;
import org.springframework.http.ResponseEntity;
import exception.NoSubjectClaimException;
import exception.UserNotFoundException;

public interface BalanceService {
    ResponseEntity<BalanceDto> getBalance(String userId) throws UserNotFoundException, NoSubjectClaimException;
}
