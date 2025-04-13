package re1kur.coinsservice.mapper;

import re1kur.coinsservice.dto.TransactionDto;
import re1kur.coinsservice.entity.Transaction;

public interface TransactionMapper {
    TransactionDto read(Transaction transaction);
}
