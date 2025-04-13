package re1kur.coinsservice.service;

import org.springframework.http.ResponseEntity;
import re1kur.coinsservice.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    ResponseEntity<List<TransactionDto>> getHistory(String subject);
}
