package re1kur.coinsservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@Table(name = "transactions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    private String userId;

    @Column(insertable = false)
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    private TransactionType type;

    private BigDecimal amount;

    private String description;
}
