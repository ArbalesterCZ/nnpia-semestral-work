package st43189.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDto {

    private long id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    private String password;
    private String oldPassword;
}
