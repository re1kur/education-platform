package re1kur.coinsservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import re1kur.coinsservice.dto.TransactionDto;
import re1kur.coinsservice.mapper.TransactionMapper;
import re1kur.coinsservice.repository.TransactionRepository;
import re1kur.coinsservice.service.TransactionService;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DefaultTransactionService implements TransactionService {
    private final TransactionRepository repo;
    private final TransactionMapper mapper;
    private final ObjectMapper serializer;

    @Override
    @SneakyThrows
    public ResponseEntity<String> getHistory(String userId) {
        Stream<TransactionDto> transactions = repo.findAllByUserId(userId).stream().map(mapper::read);
        String json = serializer.writeValueAsString(transactions);
        return ResponseEntity.status(HttpStatus.FOUND).body(json);
    }
}
