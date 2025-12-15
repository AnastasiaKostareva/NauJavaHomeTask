package ru.KostarevaAnastasia.NauJava.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.KostarevaAnastasia.NauJava.models.Role;
import ru.KostarevaAnastasia.NauJava.models.User;
import ru.KostarevaAnastasia.NauJava.service.UserService;

@Controller
@RequestMapping("/users")
public class UserWebController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "users/edit";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateUser(@PathVariable Long id, @ModelAttribute User userForm) {
        User existingUser = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        existingUser.setRole(userForm.getRole());
        userService.save(existingUser);
        return "redirect:/users";
    }
}