package st43189.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductInCartDto {

    @NotNull
    private int amount;

    private long userId;
    private long productId;
}
