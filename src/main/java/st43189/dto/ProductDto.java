package st43189.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {

    private long id;

    private BigDecimal price;
    private String name;
    private String description;
    private String image;

    private long categoryId;
}
