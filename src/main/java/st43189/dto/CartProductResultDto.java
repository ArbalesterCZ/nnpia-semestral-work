package st43189.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartProductResultDto {

    private long id;
    private int amount;

    private BigDecimal price;
    private String name;
}
