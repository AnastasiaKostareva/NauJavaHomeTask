package ru.KostarevaAnastasia.NauJava.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.KostarevaAnastasia.NauJava.models.User;
import ru.KostarevaAnastasia.NauJava.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/custom/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findByUsername")
    public Optional<User> findByName(@RequestParam String name) {
        return userService.getUsersByUsername(name);
    }

    @PostMapping("/getOrCreate")
    public User getOrCreateUser(@RequestParam String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        return userService.getOrCreateUser(username);
    }
}
