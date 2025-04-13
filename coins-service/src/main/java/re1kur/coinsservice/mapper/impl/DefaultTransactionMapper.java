package re1kur.coinsservice.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import re1kur.coinsservice.dto.TransactionDto;
import re1kur.coinsservice.entity.Transaction;
import re1kur.coinsservice.mapper.TransactionMapper;

@Component
@RequiredArgsConstructor
public class DefaultTransactionMapper implements TransactionMapper {

    @Override
    public TransactionDto read(Transaction transaction) {
        return new TransactionDto(transaction.getId().toString(), transaction.getUserId().toString(),
                transaction.getType().getName(), transaction.getAmount(),
                transaction.getDate(), transaction.getDescription());
    }
}
