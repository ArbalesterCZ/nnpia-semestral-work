package st43189.upce.cz.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {

    private BigDecimal price;
    private String name;
    private String description;

    private long categoryId;
}
