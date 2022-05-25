package st43189.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Table(name = "scores")
public class Score {
    @EmbeddedId
    private UserProductKey id;
    @Column(nullable = false)
    private int value;
    @Column
    private String comment;
    @Column
    private ZonedDateTime timestamp;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;
}
