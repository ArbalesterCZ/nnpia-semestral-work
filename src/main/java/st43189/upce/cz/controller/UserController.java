package st43189.upce.cz.controller;

import org.springframework.web.bind.annotation.*;
import st43189.upce.cz.dto.UserDto;
import st43189.upce.cz.entity.User;
import st43189.upce.cz.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto dto) {
        return toDto(userService.createOrUpdate((fromDto(dto))));
    }

    @GetMapping
    public List<UserDto> readAll() {
        return userService
                .getAll()
                .stream()
                .map(UserController::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto read(@PathVariable long id) {
        return toDto(userService.find(id));
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable long id, @RequestBody UserDto dto) {
        User user = fromDto(dto);
        user.setId(id);
        return toDto(userService.createOrUpdate(user));
    }

    @DeleteMapping("/{id}")
    public UserDto delete(@PathVariable long id) {
        return toDto(userService.delete(id));
    }

    private User fromDto(UserDto dto) {
        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(userService.encode(dto.getPassword()));

        return user;
    }

    private static UserDto toDto(User user) {
        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());

        return dto;
    }
}
