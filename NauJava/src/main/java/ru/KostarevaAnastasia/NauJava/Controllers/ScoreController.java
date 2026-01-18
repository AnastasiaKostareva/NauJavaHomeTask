package ru.KostarevaAnastasia.NauJava.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.KostarevaAnastasia.NauJava.service.ScoreService;

@Controller
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/scores")
    public String showScoresForm() {
        return "scores/form";
    }

    @PostMapping("/scores")
    public String viewScores(@RequestParam String participantName, Model model) {
        var scores = scoreService.findByUsername(participantName);
        model.addAttribute("scores", scores);
        model.addAttribute("participantName", participantName);
        return "scores/list";
    }
}
