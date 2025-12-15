package ru.KostarevaAnastasia.NauJava.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.KostarevaAnastasia.NauJava.service.ScoreService;

@Controller
public class ScoreController {
    @Autowired
    private ScoreService scoreService;

    @GetMapping("/scores")
    public String viewScores(Authentication authentication, Model model) {
        String username = authentication.getName();
        var scores = scoreService.findByUsername(username);
        model.addAttribute("scores", scores);
        return "scores/list";
    }
}
