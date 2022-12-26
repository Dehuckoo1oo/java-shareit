package ru.yandex.practicum.ShareIt.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/{userId}")
    public UserDTO remove(@PathVariable Long userId) {
        return userService.remove(userId);
    }

    @PostMapping
    public UserDTO create(@Validated(UserDTO.New.class) @RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @PatchMapping("/{userId}")
    public UserDTO update(@PathVariable Long userId, @Validated(UserDTO.Update.class) @RequestBody UserDTO userDTO) {
        userDTO.setId(userId);
        return userService.update(userDTO);
    }

    @GetMapping
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable Long userId) {
        return userService.findUserById(userId);
    }
}
