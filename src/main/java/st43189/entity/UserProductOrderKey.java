package st43189.entity;

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
public class UserProductOrderKey implements Serializable {

    @Column(name = "user_id")
    private long userId;
    @Column(name = "product_id")
    private long productId;
    @Column(name = "order_id")
    private long orderId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProductOrderKey that = (UserProductOrderKey) o;
        return userId == that.userId && productId == that.productId && orderId == that.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, productId, orderId);
    }
}
