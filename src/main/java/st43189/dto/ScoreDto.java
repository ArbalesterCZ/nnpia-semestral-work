package st43189.dto;

import lombok.Data;

@Data
public class ScoreDto {

    private int value;
    private String comment;
    private String timestamp;

    private long userId;
    private long productId;
}
