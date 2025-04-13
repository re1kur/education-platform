package re1kur.coinsservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public record BalanceDto (String userId, BigDecimal balance) implements Serializable {
}
