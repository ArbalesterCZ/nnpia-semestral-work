package st43189.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CategoryDto {

    private long id;
    @NotNull
    private String name;
}
