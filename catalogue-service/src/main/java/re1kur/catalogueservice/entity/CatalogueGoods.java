package re1kur.catalogueservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "catalogue_goods")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CatalogueGoods {
    @Id
    @Column(name = "goods_id")
    private Integer userId;

    @OneToOne
    @JoinColumn(name = "goods_id", referencedColumnName = "id",
            nullable = false, updatable = false)
    private Goods goods;

    @Column(insertable = false)
    private Integer order;
}
