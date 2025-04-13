package re1kur.coinsservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(name = "balances")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Balance {
    @Id
    private UUID userId;

    @Column(insertable = false)
    private BigDecimal balance;
}
