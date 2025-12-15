package ru.KostarevaAnastasia.NauJava.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.KostarevaAnastasia.NauJava.models.Role;
import ru.KostarevaAnastasia.NauJava.models.User;
import ru.KostarevaAnastasia.NauJava.service.UserService;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/registration")
    public String registration()
    {
        return "registration";
    }
    @PostMapping("/registration")
    public String adduser(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam(defaultValue = "USER") String role,
                          Model model)
    {
        if (!role.equals("USER") && !role.equals("CREATOR")) {
            model.addAttribute("message", "Недопустимая роль");
            return "registration";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.valueOf(role));

        try
        {
            userService.addUser(user);
            return "redirect:/login";
        }
        catch (Exception ex)
        {
            model.addAttribute("message", "Registration failed: " + ex.getMessage());
            return "registration";
        }
    }
}
