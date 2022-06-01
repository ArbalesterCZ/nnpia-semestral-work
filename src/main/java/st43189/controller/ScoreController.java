package st43189.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import st43189.dto.ScoreDto;
import st43189.entity.Score;
import st43189.entity.User;
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
    public ScoreDto createOrUpdate(@Valid @RequestBody ScoreDto dto, Authentication authentication) {
        Score input = fromDto(dto, authentication);

        Score output = scoreService.createOrUpdate(input);

        return toDto(output);
    }

    @GetMapping
    public List<ScoreDto> readAll(@RequestParam(defaultValue = "0") long productId) {
        List<ScoreDto> dtoList = new LinkedList<>();
        if (productId == 0)
            scoreService.getAll().forEach(score -> dtoList.add(toDto(score)));
        else
            scoreService.getAllOfProduct(productId).forEach(score -> dtoList.add(toDto(score)));

        return dtoList;
    }

    @DeleteMapping("/{userId}/{productId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ScoreDto delete(@PathVariable long userId, @PathVariable long productId) {
        return toDto(scoreService.delete(userId, productId));
    }

    private Score fromDto(ScoreDto dto, Authentication authentication) {
        Score score = new Score();

        score.setValue(dto.getValue());
        score.setComment(dto.getComment());

        LocalDateTime ldt = LocalDateTime.parse(dto.getTimestamp());
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        score.setTimestamp(zdt);

        User user = scoreService.findUser(authentication);

        score.setUser(user);
        score.setProduct(scoreService.findProduct(dto.getProductId()));
        score.setId(new UserProductKey(user.getId(), dto.getProductId()));

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

        dto.setUserName(score.getUser().getName());
        dto.setProductName(score.getProduct().getName());

        return dto;
    }
}
