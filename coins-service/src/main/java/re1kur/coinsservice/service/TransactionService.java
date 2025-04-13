package re1kur.coinsservice.service;

import org.springframework.http.ResponseEntity;

public interface TransactionService {
    ResponseEntity<String> getHistory(String subject);
}
