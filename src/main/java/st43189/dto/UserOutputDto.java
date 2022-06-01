package st43189.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserOutputDto {

    private long id;
    private String name;
    private String email;
}
