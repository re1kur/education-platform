package re1kur.coinsservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDto(String id, String userId,
                             String type, BigDecimal amount,
                             LocalDateTime date, String description) {
}
