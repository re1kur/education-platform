package re1kur.balanceservice.service;

import dto.BalanceDto;
import exception.NoSubjectClaimException;
import exception.UserNotFoundException;

public interface BalanceService {
    BalanceDto getBalance(String userId) throws UserNotFoundException, NoSubjectClaimException;
}
