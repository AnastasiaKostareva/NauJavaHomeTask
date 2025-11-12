package ru.KostarevaAnastasia.NauJava.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.KostarevaAnastasia.NauJava.models.UserAnswer;
import ru.KostarevaAnastasia.NauJava.repositories.custom.CustomUserAnswerRepository;

import java.util.List;

@RestController
@RequestMapping("/custom/users-answers")
public class UserAnswerController {
    @Autowired
    private CustomUserAnswerRepository userAnswerRepository;

    @GetMapping("/by-user-and-theme")
    public List<UserAnswer> findUserAnswersByUserIdAndQuestionTheme(@RequestParam Long userId, @RequestParam String theme)
    {
        if (userId == null || theme == null || theme.isBlank()) {
            throw new IllegalArgumentException("userId and theme must not be null or empty");
        }

        List<UserAnswer> answers = userAnswerRepository.findUserAnswersByUserIdAndQuestionTheme(userId, theme);
        if (answers.isEmpty()) {
            throw new ResourceNotFoundException("No answers found for user " + userId + " and theme '" + theme + "'");
        }
        return answers;
    }
}
