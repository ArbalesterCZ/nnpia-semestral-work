package st43189.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import st43189.dto.UserInputDto;
import st43189.dto.UserOutputDto;
import st43189.entity.User;
import st43189.service.UserService;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserOutputDto create(@Valid @RequestBody UserInputDto dto) {
        return toDto(userService.createOrUpdate((fromDto(dto))));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserOutputDto> readAll() {
        List<UserOutputDto> dtoList = new LinkedList<>();
        userService.getAll().forEach(user -> dtoList.add(toDto(user)));

        return dtoList;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserOutputDto read(@PathVariable long id) {
        return toDto(userService.find(id));
    }

    @GetMapping("/logged")
    public UserOutputDto readLoggedUser(Authentication authentication) {
        return toDto(userService.find(authentication.getName()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserOutputDto update(@PathVariable long id, @Valid @RequestBody UserInputDto dto) {
        User user = fromDto(dto);
        user.setId(id);
        return toDto(userService.createOrUpdate(user));
    }

    @PutMapping("/logged")
    public UserOutputDto updateLoggedUser(Authentication authentication, @Valid @RequestBody UserInputDto dto) {
        return toDto(userService.createOrUpdate(fromDto(dto), authentication, dto.getOldPassword()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserOutputDto delete(@PathVariable long id) {
        return toDto(userService.delete(id));
    }

    private User fromDto(UserInputDto dto) {
        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return user;
    }

    private UserOutputDto toDto(User user) {
        UserOutputDto dto = new UserOutputDto();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        return dto;
    }
}
