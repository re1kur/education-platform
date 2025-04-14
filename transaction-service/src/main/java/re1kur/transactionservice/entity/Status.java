package re1kur.transactionservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "statuses")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Status {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();
}
