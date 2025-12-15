package ru.KostarevaAnastasia.NauJava.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.KostarevaAnastasia.NauJava.models.Test;
import ru.KostarevaAnastasia.NauJava.models.User;
import ru.KostarevaAnastasia.NauJava.service.TestService;
import ru.KostarevaAnastasia.NauJava.service.TestSessionService;
import ru.KostarevaAnastasia.NauJava.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/tests")
public class TestController {
    @Autowired
    private TestService testService;

    @Autowired
    private TestSessionService testSessionService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listTests(Model model) {
        List<Test> tests = testService.findAll();
        model.addAttribute("tests", tests);
        return "testList";
    }

    @GetMapping("/{testId}")
    public String startTest(@PathVariable Long testId, Model model) {
        var sessionDto = testSessionService.startTest(testId);
        model.addAttribute("test", sessionDto);
        return "testStart";
    }

    @GetMapping("/{testId}/edit")
    @PreAuthorize("@testSecurityService.canEditTest(authentication, #testId)")
    public String editTest(@PathVariable Long testId, Model model) {
        Test test = testService.findWithQuestions(testId);
        if (test == null) {
            return "redirect:/tests";
        }
        model.addAttribute("test", test);
        return "testEdit";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAnyRole('CREATOR', 'ADMIN')")
    public String showCreateForm(Model model) {
        model.addAttribute("test", new Test());
        return "testForm";
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CREATOR', 'ADMIN')")
    public String createTest(@ModelAttribute Test test,
                             Authentication authentication) {
        String username = authentication.getName();
        User currentUser = userService.getUsersByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        test.setCreator(currentUser);
        testService.save(test);
        return "redirect:/tests";
    }
}
