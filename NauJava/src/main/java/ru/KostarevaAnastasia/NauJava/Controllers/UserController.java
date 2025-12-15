package ru.KostarevaAnastasia.NauJava.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.KostarevaAnastasia.NauJava.models.User;
import ru.KostarevaAnastasia.NauJava.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/custom/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/findByUsername")
    public Optional<User> findByName(@RequestParam String name) {
        return userService.getUsersByUsername(name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }
}
