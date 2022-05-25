package st43189.dto;

import lombok.Data;

@Data
public class ProductInCartDto {

    private int amount;

    private long userId;
    private long productId;
}
