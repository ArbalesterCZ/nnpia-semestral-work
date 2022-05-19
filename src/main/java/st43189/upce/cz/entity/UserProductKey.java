package st43189.upce.cz.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProductKey implements Serializable {

    @Column(name = "user_id")
    private long userId;
    @Column(name = "product_id")
    private long productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProductKey other = (UserProductKey) o;
        return Objects.equals(userId, other.userId) && Objects.equals(productId, other.productId);
    }

    @Override
    public int hashCode() { return Objects.hash(userId, productId); }
}
