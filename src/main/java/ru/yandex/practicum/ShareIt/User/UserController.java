package ru.yandex.practicum.ShareIt.User;

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
    public User remove(@PathVariable Long userId){
        return userService.remove(userId);
    }

    @PostMapping
    public User create(@Validated(User.New.class) @RequestBody User user){
        return userService.create(user);
    }

    @PatchMapping("/{userId}")
    public User update(@PathVariable Long userId,@Validated(User.Update.class)@RequestBody User user){
        user.setId(userId);
        return userService.update(user);
    }

    @GetMapping
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }
}
