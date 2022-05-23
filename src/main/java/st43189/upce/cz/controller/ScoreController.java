package st43189.upce.cz.controller;

import org.springframework.web.bind.annotation.*;
import st43189.upce.cz.dto.ScoreDto;
import st43189.upce.cz.entity.Score;
import st43189.upce.cz.service.ScoreService;

import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping
    public Score create(@RequestBody ScoreDto dto) {
        return scoreService.createOrUpdate(fromDto(dto));
    }

    @GetMapping
    public List<Score> readAll() {
        return scoreService.getAll();
    }

    @GetMapping("/{userId}/{productId}")
    public Score read(@PathVariable long userId, @PathVariable long productId) {
        return scoreService.find(userId, productId);
    }

    @PutMapping
    public Score update(@RequestBody ScoreDto dto) {
        return scoreService.createOrUpdate(fromDto(dto));
    }

    @DeleteMapping("/{userId}/{productId}")
    public Score delete(@PathVariable long userId, @PathVariable long productId) {
        return scoreService.delete(userId, productId);
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
}
