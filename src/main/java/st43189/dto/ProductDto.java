package st43189.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductDto {

    private long id;

    @Min(1)
    private BigDecimal price;
    @NotNull
    private String name;
    private String description;
    private String image;

    private long categoryId;
}
