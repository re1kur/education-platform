package re1kur.transactionservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "transaction_types")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "transactions")
public class TransactionType {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();
}
