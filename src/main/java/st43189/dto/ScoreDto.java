package st43189.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class ScoreDto {

    @Min(0)
    @Max(10)
    private int value;
    private String comment;
    private String timestamp;

    private long userId;
    private long productId;

    private String userName;
    private String productName;
}
