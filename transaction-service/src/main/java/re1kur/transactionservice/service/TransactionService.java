package re1kur.transactionservice.service;

import org.springframework.http.ResponseEntity;
import dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    ResponseEntity<List<TransactionDto>> getHistory(String subject);
}
