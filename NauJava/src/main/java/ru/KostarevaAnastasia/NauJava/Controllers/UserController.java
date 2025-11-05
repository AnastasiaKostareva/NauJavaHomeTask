package ru.KostarevaAnastasia.NauJava.Controllers;

import org.springframework.web.bind.annotation.*;
import ru.KostarevaAnastasia.NauJava.models.User;
import ru.KostarevaAnastasia.NauJava.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/custom/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/findByUsername")
    public Optional<User> findByName(@RequestParam String name) {
        return userService.getUsersByUsername(name);
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }
}
