package st43189.upce.cz.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import st43189.upce.cz.dto.UserDto;
import st43189.upce.cz.entity.User;
import st43189.upce.cz.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping
    public User create(@RequestBody UserDto dto) {
        return userService.createOrUpdate((fromDto(dto)));
    }

    @GetMapping
    public List<User> readAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User read(@PathVariable long id) {
        return userService.find(id);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable long id, @RequestBody UserDto dto) {
        User user = fromDto(dto);
        user.setId(id);
        return userService.createOrUpdate(user);
    }

    @DeleteMapping("/{id}")
    public User delete(@PathVariable long id) {
        return userService.delete(id);
    }

    private User fromDto(UserDto dto) {
        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

        return user;
    }
}
