package re1kur.transactionservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import dto.TransactionDto;
import re1kur.transactionservice.mapper.TransactionMapper;
import re1kur.transactionservice.repository.TransactionRepository;
import re1kur.transactionservice.service.TransactionService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultTransactionService implements TransactionService {
    private final TransactionRepository repo;
    private final TransactionMapper mapper;

    @Override
    @SneakyThrows
    public ResponseEntity<List<TransactionDto>> getHistory(String userId) {
        List<TransactionDto> transactions = repo.findAllByUserId(UUID.fromString(userId))
                .stream().map(mapper::read).toList();
        return ResponseEntity.status(HttpStatus.FOUND).body(transactions);
    }
}
