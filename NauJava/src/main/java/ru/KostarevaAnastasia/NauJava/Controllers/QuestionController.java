package ru.KostarevaAnastasia.NauJava.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.KostarevaAnastasia.NauJava.models.Question;
import ru.KostarevaAnastasia.NauJava.repositories.QuestionRepository;

@Controller
@RequestMapping("/questions/view")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/list")
    public String questionsListView(Model model)
    {
        Iterable<Question> products = questionRepository.findAll();
        model.addAttribute("questions", products);
        return "questionList";
    }
}
