package re1kur.transactionservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "statuses")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "transactions")
public class Status {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();
}
