package st43189.controller;

import org.springframework.web.bind.annotation.*;
import st43189.dto.ScoreDto;
import st43189.entity.Score;
import st43189.entity.UserProductKey;
import st43189.service.ScoreService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/score")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ScoreDto createOrUpdate(@Valid @RequestBody ScoreDto dto) {
        Score input = fromDto(dto);

        Score output = scoreService.createOrUpdate(input);

        return toDto(output);
    }

    @GetMapping
    public List<ScoreDto> readAll() {
        List<ScoreDto> dtoList = new LinkedList<>();
        scoreService.getAll().forEach(score -> dtoList.add(toDto(score)));

        return dtoList;
    }

    @GetMapping("/{userId}/{productId}")
    public ScoreDto read(@PathVariable long userId, @PathVariable long productId) {
        return toDto(scoreService.find(userId, productId));
    }

    @DeleteMapping("/{userId}/{productId}")
    public ScoreDto delete(@PathVariable long userId, @PathVariable long productId) {
        return toDto(scoreService.delete(userId, productId));
    }

    private Score fromDto(ScoreDto dto) {
        Score score = new Score();

        score.setValue(dto.getValue());
        score.setComment(dto.getComment());

        LocalDateTime ldt = LocalDateTime.parse(dto.getTimestamp());
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        score.setTimestamp(zdt);

        score.setUser(scoreService.findUser(dto.getUserId()));
        score.setProduct(scoreService.findProduct(dto.getProductId()));
        score.setId(new UserProductKey(dto.getUserId(), dto.getProductId()));

        return score;
    }

    private ScoreDto toDto(Score score) {
        ScoreDto dto = new ScoreDto();

        dto.setValue(score.getValue());
        dto.setComment(score.getComment());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String text = score.getTimestamp().format(formatter);
        dto.setTimestamp(text.replace(' ', 'T'));

        dto.setUserId(score.getUser().getId());
        dto.setProductId(score.getProduct().getId());

        return dto;
    }
}
