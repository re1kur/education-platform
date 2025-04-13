package re1kur.coinsservice.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import re1kur.coinsservice.dto.TransactionDto;
import re1kur.coinsservice.entity.Transaction;
import re1kur.coinsservice.mapper.TransactionMapper;

@Component
@RequiredArgsConstructor
public class DefaultTransactionMapper implements TransactionMapper {
    private final ObjectMapper serializer;


    @Override
    public TransactionDto read(Transaction transaction) {
        return new TransactionDto(transaction.getId(), transaction.getUserId(),
                transaction.getType().getName(), transaction.getAmount(),
                transaction.getDate(), transaction.getDescription());
    }
}
