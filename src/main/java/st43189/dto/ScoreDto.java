package st43189.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ScoreDto {

    private int value;
    private String comment;
    private ZonedDateTime timestamp;

    private long userId;
    private long productId;
}
