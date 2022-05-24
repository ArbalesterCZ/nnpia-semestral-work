package st43189.upce.cz.controller;

import org.springframework.web.bind.annotation.*;
import st43189.upce.cz.dto.ScoreDto;
import st43189.upce.cz.entity.Score;
import st43189.upce.cz.service.ScoreService;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
        return scoreService
                .getAll()
                .stream()
                .map(ScoreController::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public List<ScoreDto> readAllOfUser(@PathVariable long userId) {
        List<ScoreDto> dtoList = new LinkedList<>();
        scoreService.getAllOfUser(userId).forEach(score -> dtoList.add(toDto(score)));

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

    private static ScoreDto toDto(Score score) {
        ScoreDto dto = new ScoreDto();

        dto.setValue(score.getValue());
        dto.setComment(score.getComment());
        dto.setTimestamp(score.getTimestamp());

        dto.setUserId(score.getUser().getId());
        dto.setProductId(score.getProduct().getId());

        return dto;
    }
}
