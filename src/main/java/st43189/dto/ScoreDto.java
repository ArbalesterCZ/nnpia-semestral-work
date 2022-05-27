package st43189.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ScoreDto {

    @NotNull
    private int value;
    private String comment;
    private String timestamp;

    private long userId;
    private long productId;
}
