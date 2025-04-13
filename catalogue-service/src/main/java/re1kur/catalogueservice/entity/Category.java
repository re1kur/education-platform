package re1kur.catalogueservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "categories")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "goods")
public class Category {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String title;

    private String description;

    private String imageUrl;

    @OneToMany(mappedBy = "category", fetch = LAZY)
    private List<Goods> goods;
}
