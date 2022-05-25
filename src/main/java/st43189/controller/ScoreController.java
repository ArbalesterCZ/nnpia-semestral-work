package st43189.controller;

import org.springframework.web.bind.annotation.*;
import st43189.dto.ScoreDto;
import st43189.entity.Score;
import st43189.service.ScoreService;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ScoreDto createOrUpdate(@RequestBody ScoreDto dto) {
        return toDto(scoreService.createOrUpdate(fromDto(dto)));
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
        score.setTimestamp(dto.getTimestamp());

        score.setUser(scoreService.findUser(dto.getUserId()));
        score.setProduct(scoreService.findProduct(dto.getProductId()));

        return score;
    }

    private ScoreDto toDto(Score score) {
        ScoreDto scoreDto = new ScoreDto();

        scoreDto.setValue(scoreDto.getValue());
        scoreDto.setComment(scoreDto.getComment());
        scoreDto.setTimestamp(score.getTimestamp());

        scoreDto.setUserId(score.getUser().getId());
        scoreDto.setProductId(score.getProduct().getId());

        return scoreDto;
    }
}
