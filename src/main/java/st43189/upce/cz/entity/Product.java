package st43189.upce.cz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @Column
    private BigDecimal price;
    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @ManyToOne
    @JsonIgnore
    private Category category;
    @OneToMany(mappedBy = "product")
    private Set<Score> scoreSet;
    @OneToMany(mappedBy = "product")
    private Set<ProductInCart> productInCartSet;
}
