package ru.KostarevaAnastasia.NauJava.Controllers;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.KostarevaAnastasia.NauJava.dto.QuestionFormDto;
import ru.KostarevaAnastasia.NauJava.models.Option;
import ru.KostarevaAnastasia.NauJava.models.Question;
import ru.KostarevaAnastasia.NauJava.models.QuestionType;
import ru.KostarevaAnastasia.NauJava.models.User;
import ru.KostarevaAnastasia.NauJava.service.QuestionService;
import ru.KostarevaAnastasia.NauJava.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/questions")
@PreAuthorize("hasAnyRole('CREATOR', 'ADMIN')")
public class QuestionWebController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("questionForm", new QuestionFormDto("", "", QuestionType.SINGLE, List.of(), List.of()));
        model.addAttribute("questionTypes", QuestionType.values());
        return "questions/create";
    }

    @PostMapping
    public String createQuestion(
            @ModelAttribute QuestionFormDto form,
            BindingResult bindingResult,
            Authentication authentication,
            RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(e ->
                    System.out.println("Ошибка: " + e.getDefaultMessage()));
            redirectAttrs.addFlashAttribute("error", "Ошибка валидации данных");
            return "redirect:/questions/new";
        }
        Question question = new Question();
        question.setTextQuestion(form.textQuestion());
        question.setTheme(form.theme());
        question.setQuestionType(form.questionType());

        User author = userService.getUsersByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        question.setAuthor(author);
        List<Option> options = IntStream.range(0, form.optionText().size())
                .mapToObj(i -> {
                    Option opt = new Option();
                    opt.setText(form.optionText().get(i));
                    opt.setCorrect(form.optionCorrect().size() > i ? form.optionCorrect().get(i) : false);
                    return opt;
                })
                .collect(Collectors.toList());

        questionService.createQuestionWithOptions(question, options);
        redirectAttrs.addFlashAttribute("message", "Вопрос успешно создан!");
        return "redirect:/questions";
    }

    @GetMapping
    public String listQuestions(Model model) {
        model.addAttribute("questions", questionService.findAllWithOptions());
        return "questions/list";
    }
}