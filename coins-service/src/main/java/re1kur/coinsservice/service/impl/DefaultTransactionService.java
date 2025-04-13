package re1kur.coinsservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import re1kur.coinsservice.dto.TransactionDto;
import re1kur.coinsservice.mapper.TransactionMapper;
import re1kur.coinsservice.repository.TransactionRepository;
import re1kur.coinsservice.service.TransactionService;

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
