package st43189.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @Column(nullable = false)
    private String image;
    @Column(columnDefinition = "boolean default true")
    private boolean enabled;
    @ManyToOne
    private Category category;
    @OneToMany(mappedBy = "product")
    private Set<Score> scores;
    @OneToMany(mappedBy = "product")
    private Set<ProductInCart> products;
    @OneToMany(mappedBy = "product")
    private Set<ProductAtOrder> productAtOrders;
}
